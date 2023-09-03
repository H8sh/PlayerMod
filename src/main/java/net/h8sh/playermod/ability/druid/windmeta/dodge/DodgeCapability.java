package net.h8sh.playermod.ability.druid.windmeta.dodge;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class DodgeCapability {

    private static boolean onDodge;

    public static boolean getOnDodge() {
        return DodgeCapability.onDodge;
    }

    public static void setOnDodge(boolean onDodge) {
        DodgeCapability.onDodge = onDodge;
    }

    public void copyFrom(DodgeCapability source) {
        DodgeCapability.onDodge = source.onDodge;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onDodge", onDodge);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onDodge = compoundTag.getBoolean("onDodge");
    }

}