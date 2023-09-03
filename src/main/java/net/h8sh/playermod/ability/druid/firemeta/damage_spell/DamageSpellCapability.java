package net.h8sh.playermod.ability.druid.firemeta.damage_spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class DamageSpellCapability {

    private static boolean onDamageSpell;

    public static boolean getOnDamageSpell() {
        return DamageSpellCapability.onDamageSpell;
    }

    public static void setOnDamageSpell(boolean onDamageSpell) {
        DamageSpellCapability.onDamageSpell = onDamageSpell;
    }

    public void copyFrom(DamageSpellCapability source) {
        DamageSpellCapability.onDamageSpell = source.onDamageSpell;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onDamageSpell", onDamageSpell);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onDamageSpell = compoundTag.getBoolean("onDamageSpell");
    }

}