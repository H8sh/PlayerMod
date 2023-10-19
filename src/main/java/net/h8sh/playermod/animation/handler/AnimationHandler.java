package net.h8sh.playermod.animation.handler;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AnimationHandler {

    public static int countTickAnimation = 0;
    public static int animationLongInTick;
    public static boolean STEVE_BACK_DASH = false;

    public static void setAnimationLongInTick(int animationLongInTick) {
        AnimationHandler.animationLongInTick = animationLongInTick;
    }

    public static int getCountTickAnimation() {
        return countTickAnimation;
    }

    public static boolean getSteveBackDash() {
        return STEVE_BACK_DASH;
    }

    public static void setSteveBackDash(boolean bool) {
        STEVE_BACK_DASH = bool;
    }

    public static void countTickAnimation() {
        countTickAnimation = countTickAnimation < animationLongInTick ? countTickAnimation + 1 : 0;
        if (countTickAnimation == 0 && animationLongInTick != 0) setAnimationLongInTick(0);
    }

    public void copyFrom(AnimationHandler source) {
        STEVE_BACK_DASH = source.STEVE_BACK_DASH;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("steve_back_dash", STEVE_BACK_DASH);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        STEVE_BACK_DASH = compoundTag.getBoolean("steve_back_dash");
    }

}