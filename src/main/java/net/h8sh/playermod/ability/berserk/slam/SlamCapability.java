package net.h8sh.playermod.ability.berserk.slam;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class SlamCapability {

    private static boolean onSlam;

    public static boolean getOnSlam() {
        return SlamCapability.onSlam;
    }

    public static void setOnSlam(boolean onSlam) {
        SlamCapability.onSlam = onSlam;
    }

    public void copyFrom(SlamCapability source) {
        SlamCapability.onSlam = source.onSlam;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onSlam", onSlam);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onSlam = compoundTag.getBoolean("onSlam");
    }

}