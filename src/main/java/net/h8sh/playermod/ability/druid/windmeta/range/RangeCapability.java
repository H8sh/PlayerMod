package net.h8sh.playermod.ability.druid.windmeta.range;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class RangeCapability {

    private static boolean onRange;

    public static boolean getOnRange() {
        return RangeCapability.onRange;
    }

    public static void setOnRange(boolean onRange) {
        RangeCapability.onRange = onRange;
    }

    public void copyFrom(RangeCapability source) {
        RangeCapability.onRange = source.onRange;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onRange", onRange);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onRange = compoundTag.getBoolean("onRange");
    }

}