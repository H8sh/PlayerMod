package net.h8sh.playermod.ability.wizard.freeze;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class FreezeCapability {

    private static boolean onFreeze;

    public static boolean getOnFreeze() {
        return FreezeCapability.onFreeze;
    }

    public static void setOnFreeze(boolean onFreeze) {
        FreezeCapability.onFreeze = onFreeze;
    }

    public void copyFrom(FreezeCapability source) {
        FreezeCapability.onFreeze = source.onFreeze;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onFreeze", onFreeze);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onFreeze = compoundTag.getBoolean("onFreeze");
    }

}