package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.entity.custom.CameraEntity;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.camera.SpawnCameraC2SPacket;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class PlayerEvent {
    private static CameraEntity camera;

    public static CameraEntity getCamera() {
        return camera;
    }

    public static void setCamera(CameraEntity camera) {
        PlayerEvent.camera = camera;
    }

    @SubscribeEvent
    public static void onPlayerJoiningWorld(EntityJoinLevelEvent event) {
        if (event.getEntity() instanceof Player) {
            AnimationHandler.setSteveAttack(false);
            if (Minecraft.getInstance().player != null) ModMessages.sendToServer(new SpawnCameraC2SPacket());
        }
    }

    @SubscribeEvent
    public static void onPlayerDashInvincible(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player) {
            if (AnimationHandler.getSteveFrontDash() || AnimationHandler.getSteveRightDash() || AnimationHandler.getSteveLeftDash() || AnimationHandler.getSteveBackDash()) {
                event.setCanceled(true);
            }
        }
        if (event.getSource().getEntity() instanceof Player player) {
            var entity = event.getEntity();
            new Thread(() -> {
                System.out.println(DoubleCapability.getEntityPlayerLookAt(player, 20.0));
                while (Minecraft.getInstance().options.keyAttack.isDown()) {
                    entity.addEffect(new MobEffectInstance(MobEffects.POISON, 6, 20, false, true, false));
                    //TODO change to true damage
                }
            }).start();
        }
    }

}
