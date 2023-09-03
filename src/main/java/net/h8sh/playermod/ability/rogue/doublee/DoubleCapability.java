package net.h8sh.playermod.ability.rogue.doublee;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class DoubleCapability {

    private static boolean onDouble;

    public static boolean getOnDouble() {
        return DoubleCapability.onDouble;
    }

    public static void setOnDouble(boolean onDouble) {
        DoubleCapability.onDouble = onDouble;
    }

    public void copyFrom(DoubleCapability source) {
        DoubleCapability.onDouble = source.onDouble;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onDouble", onDouble);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onDouble = compoundTag.getBoolean("onDouble");
    }

}