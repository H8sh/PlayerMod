package net.h8sh.playermod.ability.paladin.armor;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class ArmorCapability {

    private static boolean onArmorBuff;

    public static boolean getOnArmorBuff() {
        return ArmorCapability.onArmorBuff;
    }

    public static void setOnArmorBuff(boolean onArmorBuff) {
        ArmorCapability.onArmorBuff = onArmorBuff;
    }

    public void copyFrom(ArmorCapability source) {
        ArmorCapability.onArmorBuff = source.onArmorBuff;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onArmorBuff", onArmorBuff);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onArmorBuff = compoundTag.getBoolean("onArmorBuff");
    }

}