package net.h8sh.playermod.ability.berserk.rage;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class RageCapability {

    private static boolean onRage;

    public static boolean getOnRage() {
        return RageCapability.onRage;
    }

    public static void setOnRage(boolean onRage) {
        RageCapability.onRage = onRage;
    }

    public void copyFrom(RageCapability source) {
        RageCapability.onRage = source.onRage;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onRage", onRage);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onRage = compoundTag.getBoolean("onRage");
    }

}