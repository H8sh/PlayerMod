package net.h8sh.playermod.ability.berserk.healthsacrifice;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class HealthSacrificeCapability {

    private static boolean onHealthSacrifice;

    public static boolean getOnHealthSacrifice() {
        return HealthSacrificeCapability.onHealthSacrifice;
    }

    public static void setOnHealthSacrifice(boolean onHealthSacrifice) {
        HealthSacrificeCapability.onHealthSacrifice = onHealthSacrifice;
    }

    public void copyFrom(HealthSacrificeCapability source) {
        HealthSacrificeCapability.onHealthSacrifice = source.onHealthSacrifice;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onHealthSacrifice", onHealthSacrifice);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onHealthSacrifice = compoundTag.getBoolean("onHealthSacrifice");
    }

}