package net.h8sh.playermod.ability.druid.firemeta.fire_aura;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class FireAuraCapability {

    private static boolean onFireAura;

    public static boolean getOnFireAura() {
        return FireAuraCapability.onFireAura;
    }

    public static void setOnFireAura(boolean onFireAura) {
        FireAuraCapability.onFireAura = onFireAura;
    }

    public void copyFrom(FireAuraCapability source) {
        FireAuraCapability.onFireAura = source.onFireAura;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onFireAura", onFireAura);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onFireAura = compoundTag.getBoolean("onFireAura");
    }

}