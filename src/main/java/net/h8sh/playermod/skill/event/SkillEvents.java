package net.h8sh.playermod.skill.event;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class SkillEvents {

    @SubscribeEvent
    public static void onPlayerLoggedIn (PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {

        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn (PlayerEvent.PlayerLoggedOutEvent event) {

    }

}
