package net.h8sh.playermod.capability.pet;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Pet {
    private static boolean hasPet = false;

    public static void setPet(boolean hasPet) {
        Pet.hasPet = hasPet;
    }

    public static boolean hasPet() {
        return hasPet;
    }

    public void copyFrom(Pet source) {
        this.hasPet = source.hasPet;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("hasPet", hasPet);
    }

    public void loadNBTData(CompoundTag nbt) {
        hasPet = nbt.getBoolean("hasPet");
    }

}
