package net.h8sh.playermod.ability.paladin.damageboost;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class DamageBoostCapability {

    private static boolean onDamageBoost;

    public static boolean getOnDamageBoost() {
        return DamageBoostCapability.onDamageBoost;
    }

    public static void setOnDamageBoost(boolean onDamageBoost) {
        DamageBoostCapability.onDamageBoost = onDamageBoost;
    }

    public void copyFrom(DamageBoostCapability source) {
        DamageBoostCapability.onDamageBoost = source.onDamageBoost;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onDamageBoost", onDamageBoost);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onDamageBoost = compoundTag.getBoolean("onDamageBoost");
    }

}