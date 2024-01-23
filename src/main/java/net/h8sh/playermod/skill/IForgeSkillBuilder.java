/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

public interface IForgeSkillBuilder {

    private Skill.Builder self() {
        return (Skill.Builder) this;
    }

    /**
     * Saves this builder with the given id using the {@link ExistingFileHelper} to check if the parent is already known.
     *
     * @param saver      a {@link Consumer} which saves any advancements provided
     * @param id         the {@link ResourceLocation} id for the new advancement
     * @param fileHelper the {@link ExistingFileHelper} where all known advancements are registered
     * @return the built advancement
     * @throws IllegalStateException if the parent of the advancement is not known
     */
    default Skill save(Consumer<Skill> saver, ResourceLocation id, ExistingFileHelper fileHelper) {
        boolean canBuild = self().canBuild((advancementId) ->
        {
            if (fileHelper.exists(advancementId, PackType.SERVER_DATA, ".json", "advancements")) {
                return new Skill(advancementId, null, null, SkillRewards.EMPTY, Maps.newHashMap(), new String[0][0], false);
            }
            return null;
        });
        if (!canBuild) {
            throw new IllegalStateException("Tried to build Advancement without valid Parent!");
        }

        Skill advancement = self().build(id);
        saver.accept(advancement);
        return advancement;
    }
}
