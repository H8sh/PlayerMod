package net.h8sh.playermod.ability.druid.spiritusmeta.aquaboost;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AquaBoostCapability {

    private static boolean onAquaBoost;

    public static boolean getOnAquaBoost() {
        return AquaBoostCapability.onAquaBoost;
    }

    public static void setOnAquaBoost(boolean onAquaBoost) {
        AquaBoostCapability.onAquaBoost = onAquaBoost;
    }

    public void copyFrom(AquaBoostCapability source) {
        AquaBoostCapability.onAquaBoost = source.onAquaBoost;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onAquaBoost", onAquaBoost);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onAquaBoost = compoundTag.getBoolean("onAquaBoost");
    }

}