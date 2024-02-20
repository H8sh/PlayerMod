package net.h8sh.playermod.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.animation.animations.AnimationManager;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.animation.SteveAttackC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class AnimationEvent {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }
        if (Minecraft.getInstance().player != null) {

            Player player = Minecraft.getInstance().player;

            AnimationHandler.countTickAnimation();

            if (AnimationManager.ANIM_ATTACK_FLAG == 0) {
                AnimationHandler.setPlayerAttack(true);
                ModMessages.sendToServer(new SteveAttackC2SPacket());
            }
            if (AnimationManager.ANIM_SHIFT_DOWN_FLAG == 0) {
                if (player.walkAnimation.isMoving()) {
                    AnimationHandler.setPlayerShiftDown(true);
                    AnimationHandler.setPlayerIdleShiftDown(false);
                }
                if (!player.walkAnimation.isMoving()) {
                    AnimationHandler.setPlayerIdleShiftDown(true);
                    AnimationHandler.setPlayerShiftDown(false);
                }

            }
            if (AnimationManager.ANIM_SHIFT_DOWN_FLAG != 0) {
                AnimationHandler.setPlayerShiftDown(false);
                AnimationHandler.setPlayerIdleShiftDown(false);
            }

        }
    }

    @SubscribeEvent
    public static void onPlayerAttackAnimation(InputEvent.MouseButton event) {
        if (event.getButton() == Minecraft.getInstance().options.keyAttack.getKey().getValue() && Minecraft.getInstance().player != null && (event.getAction() == InputConstants.PRESS || event.getAction() == InputConstants.REPEAT) && !Minecraft.getInstance().player.isDeadOrDying() && Minecraft.getInstance().screen == null) {
            AnimationManager.ANIM_ATTACK_FLAG = 0;
        } else {
            AnimationManager.ANIM_ATTACK_FLAG = 1;
        }
    }

    //For all those who are binding the sneak on extra mouse button
    @SubscribeEvent
    public static void onPlayerShiftDownAnimation(InputEvent.MouseButton event) {
        if (event.getButton() == Minecraft.getInstance().options.keyShift.getKey().getValue() && Minecraft.getInstance().player != null && (event.getAction() == InputConstants.PRESS || event.getAction() == InputConstants.REPEAT)) {
            AnimationManager.ANIM_SHIFT_DOWN_FLAG = 0;
        } else {
            AnimationManager.ANIM_SHIFT_DOWN_FLAG = 1;
        }
    }

}
