package net.h8sh.playermod.skill;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mojang.datafixers.DataFixer;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import net.h8sh.playermod.skill.criterion.CustomCriteriaTriggers;
import net.h8sh.playermod.skill.criterion.CustomCriterionTrigger;
import net.h8sh.playermod.skill.event.SkillEvent;
import net.h8sh.playermod.skill.event.SkillEventFactory;
import net.h8sh.playermod.skill.manager.ServerSkillManager;
import net.h8sh.playermod.skill.manager.SkillVisibilityEvaluator;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSelectAdvancementsTabPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.GameRules;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.Map.Entry;

public class PlayerSkills {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = (new GsonBuilder()).registerTypeAdapter(SkillProgress.class, new SkillProgress.Serializer()).registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer()).setPrettyPrinting().create();
    private static final TypeToken<Map<ResourceLocation, SkillProgress>> TYPE_TOKEN = new TypeToken<>() {
    };
    private final DataFixer dataFixer;
    private final PlayerList playerList;
    private final Path playerSavePath;
    private final Map<Skill, SkillProgress> progress = new LinkedHashMap<>();
    private final Set<Skill> visible = new HashSet<>();
    private final Set<Skill> progressChanged = new HashSet<>();
    private final Set<Skill> rootsToUpdate = new HashSet<>();
    private ServerPlayer player;
    @Nullable
    private Skill lastSelectedTab;
    private boolean isFirstPacket = true;

    public PlayerSkills(DataFixer pDataFixer, PlayerList pPlayerList, ServerSkillManager pManager, Path pPlayerSavePath, ServerPlayer pPlayer) {
        this.dataFixer = pDataFixer;
        this.playerList = pPlayerList;
        this.playerSavePath = pPlayerSavePath;
        this.player = pPlayer;
        this.load(pManager);
    }

    public void setPlayer(ServerPlayer pPlayer) {
        this.player = pPlayer;
    }

    public void stopListening() {
        for (CustomCriterionTrigger<?> criteriontrigger : CustomCriteriaTriggers.all()) {
            criteriontrigger.removePlayerListeners(this);
        }
    }

    public void reload(ServerSkillManager pManager) {
        this.stopListening();
        this.progress.clear();
        this.visible.clear();
        this.rootsToUpdate.clear();
        this.progressChanged.clear();
        this.isFirstPacket = true;
        this.lastSelectedTab = null;
        this.load(pManager);
    }

    private void registerListeners(ServerSkillManager pManager) {
        for (Skill advancement : pManager.getAllAdvancements()) {
            this.registerListeners(advancement);
        }

    }

    private void checkForAutomaticTriggers(ServerSkillManager pManager) {
        for (Skill advancement : pManager.getAllAdvancements()) {
            if (advancement.getCriteria().isEmpty()) {
                this.award(advancement, "");
                advancement.getRewards().grant(this.player);
            }
        }

    }

    private void load(ServerSkillManager pManager) {
        if (Files.isRegularFile(this.playerSavePath)) {
            try (JsonReader jsonreader = new JsonReader(Files.newBufferedReader(this.playerSavePath, StandardCharsets.UTF_8))) {
                jsonreader.setLenient(false);
                Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, Streams.parse(jsonreader));
                int i = dynamic.get("DataVersion").asInt(1343);
                dynamic = dynamic.remove("DataVersion");
                dynamic = DataFixTypes.ADVANCEMENTS.updateToCurrentVersion(this.dataFixer, dynamic, i);
                Map<ResourceLocation, SkillProgress> map = GSON.getAdapter(TYPE_TOKEN).fromJsonTree(dynamic.getValue());
                if (map == null) {
                    throw new JsonParseException("Found null for advancements");
                }

                map.entrySet().stream().sorted(Entry.comparingByValue()).forEach((p_265663_) -> {
                    Skill advancement = pManager.getAdvancement(p_265663_.getKey());
                    if (advancement == null) {
                        LOGGER.warn("Ignored advancement '{}' in progress file {} - it doesn't exist anymore?", p_265663_.getKey(), this.playerSavePath);
                    } else {
                        this.startProgress(advancement, p_265663_.getValue());
                        this.progressChanged.add(advancement);
                        this.markForVisibilityUpdate(advancement);
                    }
                });
            } catch (JsonParseException jsonparseexception) {
                LOGGER.error("Couldn't parse player advancements in {}", this.playerSavePath, jsonparseexception);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't access player advancements in {}", this.playerSavePath, ioexception);
            }
        }

        this.checkForAutomaticTriggers(pManager);
        this.registerListeners(pManager);
    }

    public void save() {
        Map<ResourceLocation, SkillProgress> map = new LinkedHashMap<>();

        for (Map.Entry<Skill, SkillProgress> entry : this.progress.entrySet()) {
            SkillProgress skillProgress = entry.getValue();
            if (skillProgress.hasProgress()) {
                map.put(entry.getKey().getId(), skillProgress);
            }
        }

        JsonElement jsonelement = GSON.toJsonTree(map);
        jsonelement.getAsJsonObject().addProperty("DataVersion", SharedConstants.getCurrentVersion().getDataVersion().getVersion());

        try {
            FileUtil.createDirectoriesSafe(this.playerSavePath.getParent());

            try (Writer writer = Files.newBufferedWriter(this.playerSavePath, StandardCharsets.UTF_8)) {
                GSON.toJson(jsonelement, writer);
            }
        } catch (IOException ioexception) {
            LOGGER.error("Couldn't save player advancements to {}", this.playerSavePath, ioexception);
        }

    }

    public boolean award(Skill pAdvancement, String pCriterionKey) {
        if (this.player instanceof net.minecraftforge.common.util.FakePlayer) return false;
        boolean flag = false;
        SkillProgress skillProgress = this.getOrStartProgress(pAdvancement);
        boolean flag1 = skillProgress.isDone();
        if (skillProgress.grantProgress(pCriterionKey)) {
            this.unregisterListeners(pAdvancement);
            this.progressChanged.add(pAdvancement);
            flag = true;
            SkillEventFactory.onAdvancementProgressedEvent(this.player, pAdvancement, skillProgress, pCriterionKey, SkillEvent.SkillProgressEvent.ProgressType.GRANT);
            if (!flag1 && skillProgress.isDone()) {
                pAdvancement.getRewards().grant(this.player);
                if (pAdvancement.getDisplay() != null && pAdvancement.getDisplay().shouldAnnounceChat() && this.player.level().getGameRules().getBoolean(GameRules.RULE_ANNOUNCE_ADVANCEMENTS)) {
                    this.playerList.broadcastSystemMessage(Component.translatable("chat.type.advancement." + pAdvancement.getDisplay().getFrame().getName(), this.player.getDisplayName(), pAdvancement.getChatComponent()), false);
                }
                SkillEventFactory.onAdvancementEarnedEvent(this.player, pAdvancement);
            }
        }

        if (!flag1 && skillProgress.isDone()) {
            this.markForVisibilityUpdate(pAdvancement);
        }

        return flag;
    }

    public boolean revoke(Skill pAdvancement, String pCriterionKey) {
        boolean flag = false;
        SkillProgress skillProgress = this.getOrStartProgress(pAdvancement);
        boolean flag1 = skillProgress.isDone();
        if (skillProgress.revokeProgress(pCriterionKey)) {
            this.registerListeners(pAdvancement);
            this.progressChanged.add(pAdvancement);
            flag = true;
            SkillEventFactory.onAdvancementProgressedEvent(this.player, pAdvancement, skillProgress, pCriterionKey, SkillEvent.SkillProgressEvent.ProgressType.REVOKE);
        }

        if (flag1 && !skillProgress.isDone()) {
            this.markForVisibilityUpdate(pAdvancement);
        }

        return flag;
    }

    private void markForVisibilityUpdate(Skill pAdvancement) {
        this.rootsToUpdate.add(pAdvancement.getRoot());
    }

    private void registerListeners(Skill pAdvancement) {
        SkillProgress skillProgress = this.getOrStartProgress(pAdvancement);
        if (!skillProgress.isDone()) {
            for (Map.Entry<String, Criterion> entry : pAdvancement.getCriteria().entrySet()) {
                CriterionProgress criterionprogress = skillProgress.getCriterion(entry.getKey());
                if (criterionprogress != null && !criterionprogress.isDone()) {
                    CriterionTriggerInstance criteriontriggerinstance = entry.getValue().getTrigger();
                    if (criteriontriggerinstance != null) {
                        CustomCriterionTrigger<CriterionTriggerInstance> criteriontrigger = CustomCriteriaTriggers.getCriterion(criteriontriggerinstance.getCriterion());
                        if (criteriontrigger != null) {
                            criteriontrigger.addPlayerListener(this, new CustomCriterionTrigger.Listener<>(criteriontriggerinstance, pAdvancement, entry.getKey()));
                        }
                    }
                }
            }

        }
    }

    private void unregisterListeners(Skill pAdvancement) {
        SkillProgress skillProgress = this.getOrStartProgress(pAdvancement);

        for (Map.Entry<String, Criterion> entry : pAdvancement.getCriteria().entrySet()) {
            CriterionProgress criterionprogress = skillProgress.getCriterion(entry.getKey());
            if (criterionprogress != null && (criterionprogress.isDone() || skillProgress.isDone())) {
                CriterionTriggerInstance criteriontriggerinstance = entry.getValue().getTrigger();
                if (criteriontriggerinstance != null) {
                    CustomCriterionTrigger<CriterionTriggerInstance> criteriontrigger = CustomCriteriaTriggers.getCriterion(criteriontriggerinstance.getCriterion());
                    if (criteriontrigger != null) {
                        criteriontrigger.removePlayerListener(this, new CustomCriterionTrigger.Listener<>(criteriontriggerinstance, pAdvancement, entry.getKey()));
                    }
                }
            }
        }

    }

    public void flushDirty(ServerPlayer pServerPlayer) {
        if (this.isFirstPacket || !this.rootsToUpdate.isEmpty() || !this.progressChanged.isEmpty()) {
            Map<ResourceLocation, SkillProgress> map = new HashMap<>();
            Set<Skill> set = new HashSet<>();
            Set<ResourceLocation> set1 = new HashSet<>();

            for (Skill advancement : this.rootsToUpdate) {
                this.updateTreeVisibility(advancement, set, set1);
            }

            this.rootsToUpdate.clear();

            for (Skill advancement1 : this.progressChanged) {
                if (this.visible.contains(advancement1)) {
                    map.put(advancement1.getId(), this.progress.get(advancement1));
                }
            }

            this.progressChanged.clear();
            if (!map.isEmpty() || !set.isEmpty() || !set1.isEmpty()) {
                pServerPlayer.connection.send(new ClientboundUpdateAdvancementsPacket(this.isFirstPacket, set, set1, map));
            }
        }

        this.isFirstPacket = false;
    }

    public void setSelectedTab(@Nullable Skill pAdvancement) {
        Skill advancement = this.lastSelectedTab;
        if (pAdvancement != null && pAdvancement.getParent() == null && pAdvancement.getDisplay() != null) {
            this.lastSelectedTab = pAdvancement;
        } else {
            this.lastSelectedTab = null;
        }

        if (advancement != this.lastSelectedTab) {
            this.player.connection.send(new ClientboundSelectAdvancementsTabPacket(this.lastSelectedTab == null ? null : this.lastSelectedTab.getId()));
        }

    }

    public SkillProgress getOrStartProgress(Skill pAdvancement) {
        SkillProgress skillProgress = this.progress.get(pAdvancement);
        if (skillProgress == null) {
            skillProgress = new SkillProgress();
            this.startProgress(pAdvancement, skillProgress);
        }

        return skillProgress;
    }

    private void startProgress(Skill pAdvancement, SkillProgress pProgress) {
        pProgress.update(pAdvancement.getCriteria(), pAdvancement.getRequirements());
        this.progress.put(pAdvancement, pProgress);
    }

    private void updateTreeVisibility(Skill pAdvancement, Set<Skill> pVisibleAdvancements, Set<ResourceLocation> pInvisibleAdvancements) {
        SkillVisibilityEvaluator.evaluateVisibility(pAdvancement, (p_265787_) -> {
            return this.getOrStartProgress(p_265787_).isDone();
        }, (p_265247_, p_265330_) -> {
            if (p_265330_) {
                if (this.visible.add(p_265247_)) {
                    pVisibleAdvancements.add(p_265247_);
                    if (this.progress.containsKey(p_265247_)) {
                        this.progressChanged.add(p_265247_);
                    }
                }
            } else if (this.visible.remove(p_265247_)) {
                pInvisibleAdvancements.add(p_265247_.getId());
            }

        });
    }
}
