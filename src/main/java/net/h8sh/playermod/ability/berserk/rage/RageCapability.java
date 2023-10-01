package net.h8sh.playermod.ability.berserk.rage;

import net.h8sh.playermod.ability.berserk.charge.ChargeCapability;
import net.h8sh.playermod.ability.berserk.slam.SlamCapability;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class RageCapability {

    private static float Fire_PROBA_UP;
    private static float BLOCK_BRAKE_PROBA_UP;
    private static float SPIKE_PROBA_UP;

    public static float getSlamDestructionUp() {
        return BLOCK_BRAKE_PROBA_UP;
    }

    public static void setSlamDestructionUp(float blockBrakeProbaUp) {
        BLOCK_BRAKE_PROBA_UP = blockBrakeProbaUp;
    }

    public static float getSlamFireUp() {
        return Fire_PROBA_UP;
    }

    public static void setSlamFireUp(float fire_PROBA_UP) {
        Fire_PROBA_UP = fire_PROBA_UP;
    }

    public static float getSpikeUp() {
        return SPIKE_PROBA_UP;
    }

    public static void setSpikeProbaUp(float spikeProbaUp) {
        SPIKE_PROBA_UP = spikeProbaUp;
    }

    public void rage() {
        setSlamFireUp(0.3F);
        setSlamDestructionUp(0.2F);
        setSpikeProbaUp(0.2F);
    }

    public static void resetRage() {
        setSlamDestructionUp(0);
        setSlamFireUp(0);
        setSpikeProbaUp(0);
        setOnRage(false);
        SlamCapability.setOnSlam(false);
    }

    private static boolean onRage;

    public static boolean getOnRage() {
        return RageCapability.onRage;
    }

    public static void setOnRage(boolean onRage) {
        RageCapability.onRage = onRage;
    }

    public void copyFrom(RageCapability source) {
        RageCapability.onRage = source.onRage;
        RageCapability.BLOCK_BRAKE_PROBA_UP = source.BLOCK_BRAKE_PROBA_UP;
        RageCapability.Fire_PROBA_UP = source.Fire_PROBA_UP;
        RageCapability.SPIKE_PROBA_UP = source.SPIKE_PROBA_UP;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onRage", onRage);
        compoundTag.putFloat("fire_proba_up", Fire_PROBA_UP);
        compoundTag.putFloat("block_brake_proba_up", BLOCK_BRAKE_PROBA_UP);
        compoundTag.putFloat("spike_proba_up", SPIKE_PROBA_UP);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onRage = compoundTag.getBoolean("onRage");
        Fire_PROBA_UP = compoundTag.getFloat("fire_proba_up");
        BLOCK_BRAKE_PROBA_UP = compoundTag.getFloat("block_brake_proba_up");
        SPIKE_PROBA_UP = compoundTag.getFloat("spike_proba_up");
    }

}