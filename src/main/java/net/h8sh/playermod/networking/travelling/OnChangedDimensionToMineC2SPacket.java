package net.h8sh.playermod.networking.travelling;

import net.h8sh.playermod.capability.travel.Travel;
import net.h8sh.playermod.world.dimension.DimensionManager;
import net.h8sh.playermod.world.dimension.DimensionMaps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OnChangedDimensionToMineC2SPacket {

    public OnChangedDimensionToMineC2SPacket() {
    }

    public OnChangedDimensionToMineC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            ResourceKey<Level> destination = DimensionManager.getResourceKey(DimensionManager.getToNextZone(DimensionMaps.MINE.getMap()));

            ServerLevel newWorld = player.getServer().getLevel(destination);

            Travel.teleportToDimension(DimensionManager.getToNextZone(DimensionMaps.MINE.getMap()), player, newWorld);
        });

        return true;
    }

}
