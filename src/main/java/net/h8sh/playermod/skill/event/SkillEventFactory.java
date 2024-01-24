package net.h8sh.playermod.skill.event;

import net.h8sh.playermod.skill.Skill;
import net.h8sh.playermod.skill.SkillProgress;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.ApiStatus;

public class SkillEventFactory {

    @ApiStatus.Internal
    public static void onAdvancementEarnedEvent(Player player, Skill earned) {
        MinecraftForge.EVENT_BUS.post(new SkillEvent(player, earned));
    }

    @ApiStatus.Internal
    public static void onAdvancementProgressedEvent(Player player, Skill progressed, SkillProgress advancementProgress, String criterion, SkillEvent.SkillProgressEvent.ProgressType progressType) {
        MinecraftForge.EVENT_BUS.post(new SkillEvent.SkillProgressEvent(player, progressed, advancementProgress, criterion, progressType));
    }

}
