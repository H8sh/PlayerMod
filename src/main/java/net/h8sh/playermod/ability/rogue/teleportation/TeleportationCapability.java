package net.h8sh.playermod.ability.rogue.teleportation;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
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

    public static Vec3 getBlockLookAt() {
        Player player = Minecraft.getInstance().player;
        HitResult hitResult = player.pick(20, Minecraft.getInstance().getPartialTick(), false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getLocation();
        } else {
            return new Vec3(player.getX(), player.getY(), player.getZ());
        }
    }

    public static void teleport(Player player) {
        Vec3 toBeTeleported = getBlockLookAt();
        BlockPos toBeCallBack = player.blockPosition();
        player.teleportTo(toBeTeleported.x, toBeTeleported.y, toBeTeleported.z);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        player.teleportTo(toBeCallBack.getX(), toBeCallBack.getY(), toBeCallBack.getZ());
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