package net.h8sh.playermod.networking.animation;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncDeltaMovementProgressS2CPacket {

    private final int deltaMovement;

    public SyncDeltaMovementProgressS2CPacket(int deltaMovement) {
        this.deltaMovement = deltaMovement;
    }

    public SyncDeltaMovementProgressS2CPacket(FriendlyByteBuf byteBuf) {
        deltaMovement = byteBuf.readInt();

    }

    public static void teleport(Player player, Direction motionDirection, String direction) {
        int toBeTeleported = 1;

        if (direction == "left") {
            switch (motionDirection) {
                case NORTH:
                    player.setDeltaMovement(-toBeTeleported, 0, 0);
                    break;
                case SOUTH:
                    player.setDeltaMovement(toBeTeleported, 0, 0);
                    break;
                case EAST:
                    player.setDeltaMovement(0, 0, -toBeTeleported);
                    break;
                case WEST:
                    player.setDeltaMovement(0, 0, toBeTeleported);
                    break;
            }
        }
        if (direction == "right") {
            switch (motionDirection) {
                case NORTH:
                    player.setDeltaMovement(toBeTeleported, 0, 0);
                    break;
                case SOUTH:
                    player.setDeltaMovement(-toBeTeleported, 0, 0);
                    break;
                case EAST:
                    player.setDeltaMovement(0, 0, toBeTeleported);
                    break;
                case WEST:
                    player.setDeltaMovement(0, 0, -toBeTeleported);
                    break;
            }
        }
        if (direction == "up") {
            switch (motionDirection) {
                case NORTH:
                    player.setDeltaMovement(0, 0, -toBeTeleported);
                    break;
                case SOUTH:
                    player.setDeltaMovement(0, 0, toBeTeleported);
                    break;
                case EAST:
                    player.setDeltaMovement(toBeTeleported, 0, 0);
                    break;
                case WEST:
                    player.setDeltaMovement(-toBeTeleported, 0, 0);
                    break;
            }
        }
        if (direction == "down") {
            switch (motionDirection) {
                case NORTH:
                    player.setDeltaMovement(0, 0, toBeTeleported);
                    break;
                case SOUTH:
                    player.setDeltaMovement(0, 0, -toBeTeleported);
                    break;
                case EAST:
                    player.setDeltaMovement(-toBeTeleported, 0, 0);
                    break;
                case WEST:
                    player.setDeltaMovement(toBeTeleported, 0, 0);

                    break;
            }
        }
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(deltaMovement);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side

            Player player = Minecraft.getInstance().player;

            switch (deltaMovement) {
                case 0:
                    teleport(player, player.getMotionDirection(), "right");
                    break;
                case 1:
                    teleport(player, player.getMotionDirection(), "left");
                    break;
                case 2:
                    teleport(player, player.getMotionDirection(), "up");
                    break;
                case 3:
                    teleport(player, player.getMotionDirection(), "down");
                    break;
            }

        });

        return true;
    }

}
