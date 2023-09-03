package net.h8sh.playermod.ability.druid.aquameta.shield;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class ShieldCapability {

    private static boolean onShield;

    public static boolean getOnShield() {
        return ShieldCapability.onShield;
    }

    public static void setOnShield(boolean onShield) {
        ShieldCapability.onShield = onShield;
    }

    public void copyFrom(ShieldCapability source) {
        ShieldCapability.onShield = source.onShield;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onShield", onShield);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onShield = compoundTag.getBoolean("onShield");
    }

}