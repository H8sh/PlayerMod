package net.h8sh.playermod.ability.wizard.laser;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class LaserCapability {

    private static boolean onLaserActivated;

    public static boolean getOnLaserActivated() {
        return LaserCapability.onLaserActivated;
    }

    public static void setOnLaserActivated(boolean onLaserActivated) {
        LaserCapability.onLaserActivated = onLaserActivated;
    }

    public void copyFrom(LaserCapability source) {
        LaserCapability.onLaserActivated = source.onLaserActivated;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onLaserActivated", onLaserActivated);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onLaserActivated = compoundTag.getBoolean("onLaserActivated");
    }

}