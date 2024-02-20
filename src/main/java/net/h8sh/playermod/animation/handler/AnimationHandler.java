package net.h8sh.playermod.animation.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AnimationHandler {

    public static int countTickAnimation = 0;
    public static int animationLongInTick;
    public static boolean PLAYER_BACK_DASH = false;
    public static boolean PLAYER_FRONT_DASH = false;
    public static boolean PLAYER_LEFT_DASH = false;
    public static boolean PLAYER_RIGHT_DASH = false;
    public static boolean PLAYER_JUMP = false;
    public static boolean PLAYER_SHIFT_DOWN = false;
    public static boolean PLAYER_IDLE_SHIFT_DOWN = false;
    public static boolean PLAYER_ATTACK = false;

    public static void setAnimationLongInTick(int animationLongInTick) {
        AnimationHandler.animationLongInTick = animationLongInTick;
    }

    public static int getCountTickAnimation() {
        return countTickAnimation;
    }

    public static void countTickAnimation() {
        countTickAnimation = countTickAnimation < animationLongInTick ? countTickAnimation + 1 : 0;
        if (countTickAnimation == 0 && animationLongInTick != 0) {
            setAnimationLongInTick(0);
            setPlayerBackDash(false);
            setPlayerFrontDash(false);
            setPlayerLeftDash(false);
            setPlayerRightDash(false);
            setPlayerJump(false);
            setPlayerAttack(false);
        }
    }

    public static boolean getPlayerBackDash() {
        return PLAYER_BACK_DASH;
    }

    public static void setPlayerBackDash(boolean bool) {
        PLAYER_BACK_DASH = bool;
    }

    public static boolean getPlayerFrontDash() {
        return PLAYER_FRONT_DASH;
    }

    public static void setPlayerFrontDash(boolean b) {
        PLAYER_FRONT_DASH = b;
    }

    public static boolean getPlayerRightDash() {
        return PLAYER_RIGHT_DASH;
    }

    public static void setPlayerRightDash(boolean b) {
        PLAYER_RIGHT_DASH = b;
    }

    public static boolean getPlayerLeftDash() {
        return PLAYER_LEFT_DASH;
    }

    public static void setPlayerLeftDash(boolean b) {
        PLAYER_LEFT_DASH = b;
    }

    public static boolean getPlayerJump() {
        return PLAYER_JUMP;
    }

    public static void setPlayerJump(boolean b) {
        PLAYER_JUMP = b;
    }

    public static boolean getPlayerShiftDown() {
        return PLAYER_SHIFT_DOWN;
    }

    public static void setPlayerShiftDown(boolean b) {
        PLAYER_SHIFT_DOWN = b;
    }

    public static boolean getPlayerAttack() {
        return PLAYER_ATTACK;
    }

    public static void setPlayerAttack(boolean b) {
        PLAYER_ATTACK = b;
    }

    public static boolean getPlayerIdleShiftDown() {
        return PLAYER_IDLE_SHIFT_DOWN;
    }

    public static void setPlayerIdleShiftDown(boolean b) {
        PLAYER_IDLE_SHIFT_DOWN = b;
    }

    public void copyFrom(AnimationHandler source) {
        PLAYER_BACK_DASH = source.PLAYER_BACK_DASH;
        PLAYER_FRONT_DASH = source.PLAYER_FRONT_DASH;
        PLAYER_RIGHT_DASH = source.PLAYER_RIGHT_DASH;
        PLAYER_LEFT_DASH = source.PLAYER_LEFT_DASH;
        PLAYER_JUMP = source.PLAYER_JUMP;
        PLAYER_SHIFT_DOWN = source.PLAYER_SHIFT_DOWN;
        PLAYER_ATTACK = source.PLAYER_ATTACK;
        PLAYER_IDLE_SHIFT_DOWN = source.PLAYER_IDLE_SHIFT_DOWN;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("player_back_dash", PLAYER_BACK_DASH);
        compoundTag.putBoolean("player_front_dash", PLAYER_FRONT_DASH);
        compoundTag.putBoolean("player_left_dash", PLAYER_LEFT_DASH);
        compoundTag.putBoolean("player_right_dash", PLAYER_RIGHT_DASH);
        compoundTag.putBoolean("player_jump", PLAYER_JUMP);
        compoundTag.putBoolean("player_attack", PLAYER_ATTACK);
        compoundTag.putBoolean("player_shift_down", PLAYER_SHIFT_DOWN);
        compoundTag.putBoolean("player_idle_shift_down", PLAYER_IDLE_SHIFT_DOWN);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        PLAYER_BACK_DASH = compoundTag.getBoolean("player_back_dash");
        PLAYER_FRONT_DASH = compoundTag.getBoolean("player_front_dash");
        PLAYER_LEFT_DASH = compoundTag.getBoolean("player_left_dash");
        PLAYER_RIGHT_DASH = compoundTag.getBoolean("player_right_dash");
        PLAYER_JUMP = compoundTag.getBoolean("player_jump");
        PLAYER_ATTACK = compoundTag.getBoolean("player_attack");
        PLAYER_SHIFT_DOWN = compoundTag.getBoolean("player_shift_down");
        PLAYER_IDLE_SHIFT_DOWN = compoundTag.getBoolean("player_idle_shift_down");
    }

}