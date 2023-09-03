package net.h8sh.playermod.ability.rogue.shot;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class ShotCapability {

    private static boolean isShooting;

    public static boolean getIsShooting() {
        return ShotCapability.isShooting;
    }

    public static void setIsShooting(boolean isShooting) {
        ShotCapability.isShooting = isShooting;
    }

    public void copyFrom(ShotCapability source) {
        ShotCapability.isShooting = source.isShooting;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("isShooting", isShooting);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        isShooting = compoundTag.getBoolean("isShooting");
    }

}