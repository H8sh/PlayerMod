/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.h8sh.playermod.skill.event;

import net.h8sh.playermod.skill.Skill;
import net.h8sh.playermod.skill.SkillProgress;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class SkillEvent extends PlayerEvent {
    private final Skill advancement;

    public SkillEvent(Player player, Skill advancement) {
        super(player);
        this.advancement = advancement;
    }

    public Skill getAdvancement() {
        return advancement;
    }


    public static class SkillEarnEvent extends SkillEvent {

        public SkillEarnEvent(Player player, Skill earned) {
            super(player, earned);
        }


        @Override
        public Skill getAdvancement() {
            return super.getAdvancement();
        }
    }


    public static class SkillProgressEvent extends SkillEvent {
        private final SkillProgress advancementProgress;
        private final String criterionName;
        private final SkillProgressEvent.ProgressType progressType;

        public SkillProgressEvent(Player player, Skill progressed, SkillProgress advancementProgress, String criterionName, SkillProgressEvent.ProgressType progressType) {
            super(player, progressed);
            this.advancementProgress = advancementProgress;
            this.criterionName = criterionName;
            this.progressType = progressType;
        }

        @Override
        public Skill getAdvancement() {
            return super.getAdvancement();
        }


        public SkillProgress getAdvancementProgress() {
            return advancementProgress;
        }


        public String getCriterionName() {
            return criterionName;
        }


        public ProgressType getProgressType() {
            return progressType;
        }

        public enum ProgressType {
            GRANT, REVOKE
        }
    }
}
