package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SkillList {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Map<ResourceLocation, Skill> advancements = Maps.newHashMap();
    private final Set<Skill> roots = Sets.newLinkedHashSet();
    private final Set<Skill> tasks = Sets.newLinkedHashSet();
    @Nullable
    private Listener listener;

    private void remove(Skill pAdvancement) {
        for (Skill advancement : pAdvancement.getChildren()) {
            this.remove(advancement);
        }

        LOGGER.info("Forgot about advancement {}", (Object) pAdvancement.getId());
        this.advancements.remove(pAdvancement.getId());
        if (pAdvancement.getParent() == null) {
            this.roots.remove(pAdvancement);
            if (this.listener != null) {
                this.listener.onRemoveAdvancementRoot(pAdvancement);
            }
        } else {
            this.tasks.remove(pAdvancement);
            if (this.listener != null) {
                this.listener.onRemoveAdvancementTask(pAdvancement);
            }
        }

    }

    public void remove(Set<ResourceLocation> pIds) {
        for (ResourceLocation resourcelocation : pIds) {
            Skill advancement = this.advancements.get(resourcelocation);
            if (advancement == null) {
                LOGGER.warn("Told to remove advancement {} but I don't know what that is", (Object) resourcelocation);
            } else {
                this.remove(advancement);
            }
        }

    }

    public void add(Map<ResourceLocation, Skill.Builder> pAdvancements) {
        Map<ResourceLocation, Skill.Builder> map = Maps.newHashMap(pAdvancements);

        while (!map.isEmpty()) {
            boolean flag = false;
            Iterator<Map.Entry<ResourceLocation, Skill.Builder>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<ResourceLocation, Skill.Builder> entry = iterator.next();
                ResourceLocation resourcelocation = entry.getKey();
                Skill.Builder advancement$builder = entry.getValue();
                if (advancement$builder.canBuild(this.advancements::get)) {
                    Skill advancement = advancement$builder.build(resourcelocation);
                    this.advancements.put(resourcelocation, advancement);
                    flag = true;
                    iterator.remove();
                    if (advancement.getParent() == null) {
                        this.roots.add(advancement);
                        if (this.listener != null) {
                            this.listener.onAddAdvancementRoot(advancement);
                        }
                    } else {
                        this.tasks.add(advancement);
                        if (this.listener != null) {
                            this.listener.onAddAdvancementTask(advancement);
                        }
                    }
                }
            }

            if (!flag) {
                for (Map.Entry<ResourceLocation, Skill.Builder> entry1 : map.entrySet()) {
                    LOGGER.error("Couldn't load advancement {}: {}", entry1.getKey(), entry1.getValue());
                }
                break;
            }
        }

        LOGGER.info("Loaded {} advancements", (int) this.advancements.size());
    }

    public void clear() {
        this.advancements.clear();
        this.roots.clear();
        this.tasks.clear();
        if (this.listener != null) {
            this.listener.onAdvancementsCleared();
        }

    }

    public Iterable<Skill> getRoots() {
        return this.roots;
    }

    public Collection<Skill> getAllAdvancements() {
        return this.advancements.values();
    }

    @Nullable
    public Skill get(ResourceLocation pId) {
        return this.advancements.get(pId);
    }

    public void setListener(@Nullable Listener pListener) {
        this.listener = pListener;
        if (pListener != null) {
            for (Skill advancement : this.roots) {
                pListener.onAddAdvancementRoot(advancement);
            }

            for (Skill advancement1 : this.tasks) {
                pListener.onAddAdvancementTask(advancement1);
            }
        }

    }

    public interface Listener {
        void onAddAdvancementRoot(Skill pAdvancement);

        void onRemoveAdvancementRoot(Skill pAdvancement);

        void onAddAdvancementTask(Skill pAdvancement);

        void onRemoveAdvancementTask(Skill pAdvancement);

        void onAdvancementsCleared();
    }
}