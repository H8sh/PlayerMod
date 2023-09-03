package net.h8sh.playermod.ability.invocator.assemble;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AssembleCapability {

    private static boolean isAssembling;

    public static boolean getIsAssembling() {
        return AssembleCapability.isAssembling;
    }

    public static void setIsAssembling(boolean isAssembling) {
        AssembleCapability.isAssembling = isAssembling;
    }

    public void copyFrom(AssembleCapability source) {
        AssembleCapability.isAssembling = source.isAssembling;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("isAssembling", isAssembling);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        isAssembling = compoundTag.getBoolean("isAssembling");
    }

}