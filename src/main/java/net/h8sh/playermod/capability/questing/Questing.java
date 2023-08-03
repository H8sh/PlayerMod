package net.h8sh.playermod.capability.questing;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Questing {
    private static int questing;


    public static int getQuesting() {
        return questing;
    }


    public void addQuesting(int questing) {
        this.questing += questing;
    }

    public void resetQuesting() {
        this.questing = 0;
    }

    public void copyFrom(Questing source) {
        this.questing = source.questing;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("questing", questing);
    }

    public void loadNBTData(CompoundTag nbt) {
        questing = nbt.getInt("questing");
    }

}
