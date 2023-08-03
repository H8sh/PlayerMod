package net.h8sh.playermod.networking.travelling;

import net.h8sh.playermod.world.dimension.ModDimensions;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OnChangedDimensionToWonderlandsHomeC2SPacket {

    public OnChangedDimensionToWonderlandsHomeC2SPacket() {
    }

    public OnChangedDimensionToWonderlandsHomeC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            ResourceKey<Level> destination = ModDimensions.WONDERLANDS_KEY;

            ServerLevel newWorld = player.getServer().getLevel(destination);
            TravelManager.teleport(player, newWorld, new BlockPos(0, 0, 0), true);

            if (!TravelManager.get(player.serverLevel()).asAlreadyTravel("wonderlands_home")) {

                TravelManager.createPortal(newWorld,
                        new BlockPos(player.blockPosition().getX() - 2,
                                player.blockPosition().getY() - 1,
                                player.blockPosition().getZ() - 2));

                player.teleportTo(player.position().x + 0.5, player.position().y, player.position().z + 0.5);

                player.sendSystemMessage(Component.literal("Here for the first time").withStyle(ChatFormatting.AQUA));

                TravelManager.get(player.serverLevel()).usedToTravel("wonderlands_home", player.blockPosition());

            } else {

                BlockPos pos = TravelManager.get(player.serverLevel()).getPos("wonderlands_home");

                player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5); //BlockPos cast player coords to integers so we have to add 0.5 again for centering the player

                player.sendSystemMessage(Component.literal("Already been there").withStyle(ChatFormatting.RED));
            }

        });

        return true;
    }

}
