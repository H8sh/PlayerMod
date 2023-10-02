package net.h8sh.playermod.networking.utils;

import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DashC2SPacket {

    public DashC2SPacket() {
    }

    public DashC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public static void teleport(Player player, Direction motionDirection, String direction) {
        int toBeTeleported = 2;

        if (direction == "left") {
            switch (motionDirection) {
                case WEST:
                    player.teleportTo(player.getX() - toBeTeleported, player.getY(), player.getZ());
                    break;
                case EAST:
                    player.teleportTo(player.getX() + toBeTeleported, player.getY(), player.getZ());
                    break;
                case NORTH:
                    player.teleportTo(player.getX(), player.getY(), player.getZ() - toBeTeleported);
                    break;
                case SOUTH:
                    player.teleportTo(player.getX(), player.getY(), player.getZ() + toBeTeleported);
                    break;
            }
        }
        if (direction == "right") {
            switch (motionDirection) {
                case WEST:
                    player.teleportTo(player.getX() + toBeTeleported, player.getY(), player.getZ());
                    break;
                case EAST:
                    player.teleportTo(player.getX() - toBeTeleported, player.getY(), player.getZ());
                    break;
                case NORTH:
                    player.teleportTo(player.getX(), player.getY(), player.getZ() + toBeTeleported);
                    break;
                case SOUTH:
                    player.teleportTo(player.getX(), player.getY(), player.getZ() - toBeTeleported);
                    break;
            }
        }
        if (direction == "up") {
            switch (motionDirection) {
                case WEST:
                    player.teleportTo(player.getX() - toBeTeleported, player.getY(), player.getZ() - toBeTeleported);
                    break;
                case EAST:
                    player.teleportTo(player.getX() + toBeTeleported, player.getY(), player.getZ() + toBeTeleported);
                    break;
                case NORTH:
                    player.teleportTo(player.getX() + toBeTeleported, player.getY(), player.getZ());
                    break;
                case SOUTH:
                    player.teleportTo(player.getX() - toBeTeleported, player.getY(), player.getZ());
                    break;
            }
        }
        if (direction == "down") {
            switch (motionDirection) {
                case WEST:
                    player.teleportTo(player.getX() + toBeTeleported, player.getY(), player.getZ() - toBeTeleported);
                    break;
                case EAST:
                    player.teleportTo(player.getX() - toBeTeleported, player.getY(), player.getZ() + toBeTeleported);
                    break;
                case NORTH:
                    player.teleportTo(player.getX() - toBeTeleported, player.getY(), player.getZ());
                    break;
                case SOUTH:
                    player.teleportTo(player.getX() + toBeTeleported, player.getY(), player.getZ());
                    break;
            }
        }
    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyRight.isDown()) {
                teleport(player, player.getMotionDirection(), "right");

            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyLeft.isDown()) {
                teleport(player, player.getMotionDirection(), "left");
            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyUp.isDown()) {
                teleport(player, player.getMotionDirection(), "up");
            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyDown.isDown()) {
                teleport(player, player.getMotionDirection(), "down");
            }
        });

        return true;
    }

}
