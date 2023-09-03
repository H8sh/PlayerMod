package net.h8sh.playermod.ability.paladin.heal;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class HealCapability {

    private static boolean onHealing;

    public static boolean getOnHealing() {
        return HealCapability.onHealing;
    }

    public static void setOnHealing(boolean onHealing) {
        HealCapability.onHealing = onHealing;
    }

    public void copyFrom(HealCapability source) {
        HealCapability.onHealing = source.onHealing;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onHealing", onHealing);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onHealing = compoundTag.getBoolean("onHealing");
    }

}