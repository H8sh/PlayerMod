package net.h8sh.playermod.ability.druid.aquameta.wateraoe;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class WaterAoECapability {

    private static boolean onWaterAoE;

    public static boolean getOnWaterAoE() {
        return WaterAoECapability.onWaterAoE;
    }

    public static void setOnWaterAoE(boolean onWaterAoE) {
        WaterAoECapability.onWaterAoE = onWaterAoE;
    }

    public void copyFrom(WaterAoECapability source) {
        WaterAoECapability.onWaterAoE = source.onWaterAoE;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onWaterAoE", onWaterAoE);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onWaterAoE = compoundTag.getBoolean("onWaterAoE");
    }

}