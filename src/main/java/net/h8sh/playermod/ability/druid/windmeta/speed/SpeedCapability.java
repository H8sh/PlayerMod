package net.h8sh.playermod.ability.druid.windmeta.speed;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class SpeedCapability {

    private static boolean onSpeed;

    public static boolean getOnSpeed() {
        return SpeedCapability.onSpeed;
    }

    public static void setOnSpeed(boolean onSpeed) {
        SpeedCapability.onSpeed = onSpeed;
    }

    public void copyFrom(SpeedCapability source) {
        SpeedCapability.onSpeed = source.onSpeed;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onSpeed", onSpeed);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onSpeed = compoundTag.getBoolean("onSpeed");
    }

}