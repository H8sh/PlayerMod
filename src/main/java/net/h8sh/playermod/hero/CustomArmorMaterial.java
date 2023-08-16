package net.h8sh.playermod.hero;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

public interface CustomArmorMaterial {
    int getDurabilityForType(CustomArmorItem.Type pType);

    int getDefenseForType(CustomArmorItem.Type pType);

    int getEnchantmentValue();

    SoundEvent getEquipSound();

    Ingredient getRepairIngredient();

    String getName();

    float getToughness();

    /**
     * Gets the percentage of knockback resistance provided by armor of the material.
     */
    float getKnockbackResistance();
}