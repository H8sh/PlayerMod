package net.h8sh.playermod.ability.druid.spiritusmeta.windboost;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class WindBoostCapability {

    private static boolean onWindBoost;

    public static boolean getOnWindBoost() {
        return WindBoostCapability.onWindBoost;
    }

    public static void setOnWindBoost(boolean onWindBoost) {
        WindBoostCapability.onWindBoost = onWindBoost;
    }

    public void copyFrom(WindBoostCapability source) {
        WindBoostCapability.onWindBoost = source.onWindBoost;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onWindBoost", onWindBoost);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onWindBoost = compoundTag.getBoolean("onWindBoost");
    }

}