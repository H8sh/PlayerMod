package net.h8sh.playermod.ability.invocator.target;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class TargetCapability {

    private static boolean onTargeting;

    public static boolean getOnTargeting() {
        return TargetCapability.onTargeting;
    }

    public static void setOnTargeting(boolean onTargeting) {
        TargetCapability.onTargeting = onTargeting;
    }

    public void copyFrom(TargetCapability source) {
        TargetCapability.onTargeting = source.onTargeting;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onTargeting", onTargeting);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onTargeting = compoundTag.getBoolean("onTargeting");
    }

}