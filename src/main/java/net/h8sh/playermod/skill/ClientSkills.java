package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.telemetry.WorldSessionTelemetryManager;
import net.minecraft.network.protocol.game.ClientboundUpdateAdvancementsPacket;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class ClientSkills {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Minecraft minecraft;
    private final WorldSessionTelemetryManager telemetryManager;
    private final SkillList skills = new SkillList();
    private final Map<Skill, SkillProgress> progress = Maps.newHashMap();
    @Nullable
    private Listener listener;
    @Nullable
    private Skill selectedTab;

    public ClientSkills(Minecraft pMinecraft, WorldSessionTelemetryManager pTelemetryManager) {
        this.minecraft = pMinecraft;
        this.telemetryManager = pTelemetryManager;
    }

    public void update(ClientboundUpdateSkillsPacket pPacket) {
        if (pPacket.shouldReset()) {
            this.skills.clear();
            this.progress.clear();
        }

        this.skills.remove(pPacket.getRemoved());
        this.skills.add(pPacket.getAdded());

        for (Map.Entry<ResourceLocation, SkillProgress> entry : pPacket.getProgress().entrySet()) {
            Skill skill = this.skills.get(entry.getKey());
            if (skill != null) {
                SkillProgress skillProgress = entry.getValue();
                skillProgress.update(skill.getCriteria(), skill.getRequirements());
                this.progress.put(skill, skillProgress);
                if (this.listener != null) {
                    this.listener.onUpdateAdvancementProgress(skill, skillProgress);
                }

                if (!pPacket.shouldReset() && skillProgress.isDone()) {
                    if (this.minecraft.level != null) {
                        this.telemetryManager.onAdvancementDone(this.minecraft.level, skill);
                    }

                    if (skill.getDisplay() != null && skill.getDisplay().shouldShowToast()) {
                        this.minecraft.getToasts().addToast(new SkillToast(skill));
                    }
                }
            } else {
                LOGGER.warn("Server informed client about progress for unknown advancement {}", entry.getKey());
            }
        }

    }

    public SkillList getSkills() {
        return this.skills;
    }

    public void setSelectedTab(@Nullable Skill skill, boolean pTellServer) {
        ClientPacketListener clientpacketlistener = this.minecraft.getConnection();
        if (clientpacketlistener != null && skill != null && pTellServer) {
            clientpacketlistener.send(ServerboundSeenAdvancementsPacket.openedTab(skill));
        }

        if (this.selectedTab != skill) {
            this.selectedTab = skill;
            if (this.listener != null) {
                this.listener.onSelectedTabChanged(skill);
            }
        }

    }

    public void setListener(@Nullable Listener pListener) {
        this.listener = pListener;
        this.skills.setListener(pListener);
        if (pListener != null) {
            for (Map.Entry<Skill, SkillProgress> entry : this.progress.entrySet()) {
                pListener.onUpdateAdvancementProgress(entry.getKey(), entry.getValue());
            }

            pListener.onSelectedTabChanged(this.selectedTab);
        }

    }

    @OnlyIn(Dist.CLIENT)
    public interface Listener extends SkillList.Listener {
        void onUpdateAdvancementProgress(Skill skill, SkillProgress pProgress);

        void onSelectedTabChanged(@Nullable Skill skill);
    }
}