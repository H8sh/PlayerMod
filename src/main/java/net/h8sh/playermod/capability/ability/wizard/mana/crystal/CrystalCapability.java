package net.h8sh.playermod.capability.ability.wizard.mana.crystal;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class CrystalCapability {

    private int crystal;

    public int getCrystal() {
        return crystal;
    }

    public void addCrystal(int crystal) {
        this.crystal += crystal;
    }

    public void removeCrystal(int crystal) {
        this.crystal -= crystal;
    }

    public void setCrystal(int crystal){
        this.crystal = crystal;
    }

    public void copyFrom(CrystalCapability source) {
        this.crystal = source.crystal;
    }
    public void savedNBTData(CompoundTag compoundTag){
        compoundTag.putInt("crystal", crystal);
    }

    public void loadNBTData(CompoundTag compoundTag){
        compoundTag.getInt("crystal");
    }

}