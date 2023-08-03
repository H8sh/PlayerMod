package net.h8sh.playermod.networking.narrator;

import net.h8sh.playermod.capability.narrator.NarratorManager;
import net.h8sh.playermod.networking.ModMessages;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NarratorSendEndMessageToPlayerC2SPacket {

    public NarratorSendEndMessageToPlayerC2SPacket() {
    }

    public NarratorSendEndMessageToPlayerC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            ModMessages.sendToPlayer(new NarratorDataSyncS2CPacket(NarratorManager.STEVE_NARRATOR, NarratorManager.GET_END_NARRATOR), player);

        });

        return true;
    }

}
