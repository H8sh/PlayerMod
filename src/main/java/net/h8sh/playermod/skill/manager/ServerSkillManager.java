package net.h8sh.playermod.skill.manager;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.h8sh.playermod.skill.Skill;
import net.h8sh.playermod.skill.SkillList;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootDataManager;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;

public class ServerSkillManager extends SimpleJsonResourceReloadListener {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = (new GsonBuilder()).create();
    private final LootDataManager lootData;
    private final net.minecraftforge.common.crafting.conditions.ICondition.IContext context; //Forge: add context
    private SkillList skills = new SkillList();

    @Deprecated
    public ServerSkillManager(LootDataManager pLootData) {
        this(pLootData, net.minecraftforge.common.crafting.conditions.ICondition.IContext.EMPTY);
    }

    public ServerSkillManager(LootDataManager pLootData, net.minecraftforge.common.crafting.conditions.ICondition.IContext context) {
        super(GSON, "advancements");
        this.lootData = pLootData;
        this.context = context;
    }

    /**
     * Applies the prepared sound event registrations and caches to the sound manager.
     *
     * @param pObject          The prepared sound event registrations and caches
     * @param pResourceManager The resource manager
     * @param pProfiler        The profiler
     */
    protected void apply(Map<ResourceLocation, JsonElement> pObject, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        Map<ResourceLocation, Skill.Builder> map = Maps.newHashMap();
        pObject.forEach((p_278903_, p_278904_) -> {
            try {
                JsonObject jsonobject = GsonHelper.convertToJsonObject(p_278904_, "advancement");
                Skill.Builder skill$builder = Skill.Builder.fromJson(jsonobject, new DeserializationContext(p_278903_, this.lootData), this.context);
                if (skill$builder == null) {
                    LOGGER.debug("Skipping loading advancement {} as its conditions were not met", p_278903_);
                    return;
                }
                map.put(p_278903_, skill$builder);
            } catch (Exception exception) {
                LOGGER.error("Parsing error loading custom advancement {}: {}", p_278903_, exception.getMessage());
            }

        });
        SkillList skillList = new SkillList();
        skillList.add(map);

        for (Skill skill : skillList.getRoots()) {
            if (skill.getDisplay() != null) {
                TreeNodePosition.run(skill);
            }
        }

        this.skills = skillList;
    }

    @Nullable
    public Skill getAdvancement(ResourceLocation pId) {
        return this.skills.get(pId);
    }

    public Collection<Skill> getAllAdvancements() {
        return this.skills.getAllAdvancements();
    }
}
