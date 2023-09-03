package net.h8sh.playermod.ability.druid.aquameta.grab;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class GrabCapability {

    private static boolean isGrabbing;

    public static boolean getIsGrabbing() {
        return GrabCapability.isGrabbing;
    }

    public static void setIsGrabbing(boolean isGrabbing) {
        GrabCapability.isGrabbing = isGrabbing;
    }

    public void copyFrom(GrabCapability source) {
        GrabCapability.isGrabbing = source.isGrabbing;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("isGrabbing", isGrabbing);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        isGrabbing = compoundTag.getBoolean("isGrabbing");
    }

}