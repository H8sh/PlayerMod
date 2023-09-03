package net.h8sh.playermod.ability.rogue.teleportation;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class TeleportationCapability {

    private static boolean isTeleporting;

    public static boolean getIsTeleporting() {
        return TeleportationCapability.isTeleporting;
    }

    public static void setIsTeleporting(boolean isTeleporting) {
        TeleportationCapability.isTeleporting = isTeleporting;
    }

    public void copyFrom(TeleportationCapability source) {
        TeleportationCapability.isTeleporting = source.isTeleporting;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("isTeleporting", isTeleporting);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        isTeleporting = compoundTag.getBoolean("isTeleporting");
    }

}