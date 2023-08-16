package net.h8sh.playermod.networking.reputation;

import net.h8sh.playermod.capability.reputation.ReputationProvider;
import net.h8sh.playermod.capability.riding.Riding;
import net.h8sh.playermod.capability.riding.RidingProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ReputationNormalC2SPacket {

    public ReputationNormalC2SPacket() {
    }

    public ReputationNormalC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            player.getCapability(ReputationProvider.REPUTATION).ifPresent(reputation -> {

                //TODO

            });

        });

        return true;
    }

}
