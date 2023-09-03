package net.h8sh.playermod.ability.rogue.smoke;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class SmokeCapability {

    private static boolean onSmoke;

    public static boolean getOnSmoke() {
        return SmokeCapability.onSmoke;
    }

    public static void setOnSmoke(boolean onSmoke) {
        SmokeCapability.onSmoke = onSmoke;
    }

    public void copyFrom(SmokeCapability source) {
        SmokeCapability.onSmoke = source.onSmoke;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onSmoke", onSmoke);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onSmoke = compoundTag.getBoolean("onSmoke");
    }

}