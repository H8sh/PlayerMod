package net.h8sh.playermod.ability.druid.spiritusmeta.fireboost;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class FireBoostCapability {

    private static boolean onFireBoost;

    public static boolean getOnFireBoost() {
        return FireBoostCapability.onFireBoost;
    }

    public static void setOnFireBoost(boolean onFireBoost) {
        FireBoostCapability.onFireBoost = onFireBoost;
    }

    public void copyFrom(FireBoostCapability source) {
        FireBoostCapability.onFireBoost = source.onFireBoost;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onFireBoost", onFireBoost);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onFireBoost = compoundTag.getBoolean("onFireBoost");
    }

}