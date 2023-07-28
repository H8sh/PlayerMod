package net.h8sh.playermod.capability.ability.wizard.aoe;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class MagicAoECapability {

    private int magicAoE;

    public int getMagicAoE() {
        return magicAoE;
    }

    public void setMagicAoE(int magicAoE){
        this.magicAoE = magicAoE;
    }

    public void addMana(int magicAoE) {
        this.magicAoE += magicAoE;
    }

    public void copyFrom(MagicAoECapability source) {
        this.magicAoE = source.magicAoE;
    }
    public void savedNBTData(CompoundTag compoundTag){
        compoundTag.putInt("magic_aoe", magicAoE);
    }

    public void loadNBTData(CompoundTag compoundTag){
        magicAoE = compoundTag.getInt("magic_aoe");
    }

}