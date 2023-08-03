package net.h8sh.playermod.networking.narrator;

import net.h8sh.playermod.capability.narrator.ClientNarratorData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NarratorDataSyncS2CPacket {

    private final String narrator;
    private final String message;

    public NarratorDataSyncS2CPacket(String narrator, String message) {
        this.narrator = narrator;
        this.message = message;
    }

    public NarratorDataSyncS2CPacket(FriendlyByteBuf byteBuf) {
        this.narrator = byteBuf.readUtf();
        this.message = byteBuf.readUtf();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeUtf(narrator);
        byteBuf.writeUtf(message);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side

            ClientNarratorData.setNarrator(narrator);
            ClientNarratorData.setMessage(message);

        });

        return true;
    }

}
