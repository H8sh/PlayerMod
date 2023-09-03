package net.h8sh.playermod.ability.paladin.lightningstrike;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class LightningStrikeCapability {

    private static boolean onLightningStrike;

    public static boolean getOnLightningStrike() {
        return LightningStrikeCapability.onLightningStrike;
    }

    public static void setOnLightningStrike(boolean onLightningStrike) {
        LightningStrikeCapability.onLightningStrike = onLightningStrike;
    }

    public void copyFrom(LightningStrikeCapability source) {
        LightningStrikeCapability.onLightningStrike = source.onLightningStrike;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onLightningStrike", onLightningStrike);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onLightningStrike = compoundTag.getBoolean("onLightningStrike");
    }

}