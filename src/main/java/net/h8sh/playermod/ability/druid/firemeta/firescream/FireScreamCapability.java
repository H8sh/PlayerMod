package net.h8sh.playermod.ability.druid.firemeta.firescream;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class FireScreamCapability {

    private static boolean onFireScream;

    public static boolean getOnFireScream() {
        return FireScreamCapability.onFireScream;
    }

    public static void setOnFireScream(boolean onFireScream) {
        FireScreamCapability.onFireScream = onFireScream;
    }

    public void copyFrom(FireScreamCapability source) {
        FireScreamCapability.onFireScream = source.onFireScream;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onFireScream", onFireScream);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onFireScream = compoundTag.getBoolean("onFireScream");
    }

}