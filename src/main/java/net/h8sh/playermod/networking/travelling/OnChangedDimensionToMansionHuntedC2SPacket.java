package net.h8sh.playermod.networking.travelling;

import net.h8sh.playermod.world.dimension.mansion.MansionManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OnChangedDimensionToMansionHuntedC2SPacket {

    public OnChangedDimensionToMansionHuntedC2SPacket() {
    }

    public OnChangedDimensionToMansionHuntedC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            ResourceKey<Level> destination = Level.OVERWORLD;
            ServerLevel newWorld = player.getServer().getLevel(destination);

            MansionManager.getTemplatesLocationFromMap();


        });

        return true;
    }

}
