package net.h8sh.playermod.capability.ability.wizard.mana;

import net.minecraft.nbt.CompoundTag;

public class ManaCapability {

    private int mana;

    public int getMana() {
        return mana;
    }

    public void setMana(int mana){
        this.mana = mana;
    }

    public void addMana(int mana) {
        this.mana += mana;
    }

    public void copyFrom(ManaCapability source) {
        this.mana = source.mana;
    }
    public void savedNBTData(CompoundTag compoundTag){
        compoundTag.putInt("mana",mana);
    }

    public void loadNBTData(CompoundTag compoundTag){
        mana = compoundTag.getInt("mana");
    }

}
