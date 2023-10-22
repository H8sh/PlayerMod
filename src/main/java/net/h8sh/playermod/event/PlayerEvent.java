package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class PlayerEvent {

    @SubscribeEvent
    public static void onPlayerJoiningWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {
            AnimationHandler.setSteveAttack(false);
        }
    }

    @SubscribeEvent
    public static void onPlayerDashInvincible(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            if (AnimationHandler.getSteveFrontDash() || AnimationHandler.getSteveRightDash() || AnimationHandler.getSteveLeftDash() || AnimationHandler.getSteveBackDash()) {
                event.setCanceled(true);
            }
        }
    }

}
