package net.h8sh.playermod.ability.berserk.charge;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class ChargeCapability {

    private static boolean onCharge;

    public static boolean getOnCharge() {
        return ChargeCapability.onCharge;
    }

    public static void setOnCharge(boolean onCharge) {
        ChargeCapability.onCharge = onCharge;
    }

    public void copyFrom(ChargeCapability source) {
        ChargeCapability.onCharge = source.onCharge;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onCharge", onCharge);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onCharge = compoundTag.getBoolean("onCharge");
    }

}