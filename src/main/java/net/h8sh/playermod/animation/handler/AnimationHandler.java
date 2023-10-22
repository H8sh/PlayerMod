package net.h8sh.playermod.animation.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AnimationHandler {

    public static int countTickAnimation = 0;
    public static int animationLongInTick;
    public static boolean STEVE_BACK_DASH = false;
    public static boolean STEVE_FRONT_DASH = false;
    public static boolean STEVE_LEFT_DASH = false;
    public static boolean STEVE_RIGHT_DASH = false;
    public static boolean STEVE_JUMP = false;
    public static boolean STEVE_SHIFT_DOWN = false;
    public static boolean STEVE_IDLE_SHIFT_DOWN = false;
    public static boolean STEVE_ATTACK = false;

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
            setSteveBackDash(false);
            setSteveFrontDash(false);
            setSteveLeftDash(false);
            setSteveRightDash(false);
            setSteveJump(false);
            setSteveAttack(false);
        }
    }

    public static boolean getSteveBackDash() {
        return STEVE_BACK_DASH;
    }

    public static void setSteveBackDash(boolean bool) {
        STEVE_BACK_DASH = bool;
    }

    public static boolean getSteveFrontDash() {
        return STEVE_FRONT_DASH;
    }

    public static void setSteveFrontDash(boolean b) {
        STEVE_FRONT_DASH = b;
    }

    public static boolean getSteveRightDash() {
        return STEVE_RIGHT_DASH;
    }

    public static void setSteveRightDash(boolean b) {
        STEVE_RIGHT_DASH = b;
    }

    public static boolean getSteveLeftDash() {
        return STEVE_LEFT_DASH;
    }

    public static void setSteveLeftDash(boolean b) {
        STEVE_LEFT_DASH = b;
    }

    public static boolean getSteveJump() {
        return STEVE_JUMP;
    }

    public static void setSteveJump(boolean b) {
        STEVE_JUMP = b;
    }

    public static boolean getSteveShiftDown() {
        return STEVE_SHIFT_DOWN;
    }

    public static void setSteveShiftDown(boolean b) {
        STEVE_SHIFT_DOWN = b;
    }

    public static boolean getSteveAttack() {
        return STEVE_ATTACK;
    }

    public static void setSteveAttack(boolean b) {
        STEVE_ATTACK = b;
    }

    public static boolean getSteveIdleShiftDown() {
        return STEVE_IDLE_SHIFT_DOWN;
    }

    public static void setSteveIdleShiftDown(boolean b) {
        STEVE_IDLE_SHIFT_DOWN = b;
    }

    public void copyFrom(AnimationHandler source) {
        STEVE_BACK_DASH = source.STEVE_BACK_DASH;
        STEVE_FRONT_DASH = source.STEVE_FRONT_DASH;
        STEVE_RIGHT_DASH = source.STEVE_RIGHT_DASH;
        STEVE_LEFT_DASH = source.STEVE_LEFT_DASH;
        STEVE_JUMP = source.STEVE_JUMP;
        STEVE_SHIFT_DOWN = source.STEVE_SHIFT_DOWN;
        STEVE_ATTACK = source.STEVE_ATTACK;
        STEVE_IDLE_SHIFT_DOWN = source.STEVE_IDLE_SHIFT_DOWN;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("steve_back_dash", STEVE_BACK_DASH);
        compoundTag.putBoolean("steve_front_dash", STEVE_FRONT_DASH);
        compoundTag.putBoolean("steve_left_dash", STEVE_LEFT_DASH);
        compoundTag.putBoolean("steve_right_dash", STEVE_RIGHT_DASH);
        compoundTag.putBoolean("steve_jump", STEVE_JUMP);
        compoundTag.putBoolean("steve_attack", STEVE_ATTACK);
        compoundTag.putBoolean("steve_shift_down", STEVE_SHIFT_DOWN);
        compoundTag.putBoolean("steve_idle_shift_down", STEVE_IDLE_SHIFT_DOWN);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        STEVE_BACK_DASH = compoundTag.getBoolean("steve_back_dash");
        STEVE_FRONT_DASH = compoundTag.getBoolean("steve_front_dash");
        STEVE_LEFT_DASH = compoundTag.getBoolean("steve_left_dash");
        STEVE_RIGHT_DASH = compoundTag.getBoolean("steve_right_dash");
        STEVE_JUMP = compoundTag.getBoolean("steve_jump");
        STEVE_ATTACK = compoundTag.getBoolean("steve_attack");
        STEVE_SHIFT_DOWN = compoundTag.getBoolean("steve_shift_down");
        STEVE_IDLE_SHIFT_DOWN = compoundTag.getBoolean("steve_idle_shift_down");
    }

}