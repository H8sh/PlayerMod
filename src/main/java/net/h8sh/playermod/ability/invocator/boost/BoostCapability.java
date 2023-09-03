package net.h8sh.playermod.ability.invocator.boost;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class BoostCapability {

    private static boolean onBoost;

    public static boolean getOnBoost() {
        return BoostCapability.onBoost;
    }

    public static void setOnBoost(boolean onBoost) {
        BoostCapability.onBoost = onBoost;
    }

    public void copyFrom(BoostCapability source) {
        BoostCapability.onBoost = source.onBoost;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onBoost", onBoost);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onBoost = compoundTag.getBoolean("onBoost");
    }

}