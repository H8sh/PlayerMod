package net.h8sh.playermod.networking.reputation;

import net.h8sh.playermod.capability.reputation.ClientReputationData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncReputationToClientS2CPacket {

    private int reputation;
    private float reputationProgress;
    private int reputationNeeded;

    public SyncReputationToClientS2CPacket(int reputation, float reputationProgress, int reputationNeeded) {
        this.reputation = reputation;
        this.reputationProgress = reputationProgress;
        this.reputationNeeded = reputationNeeded;
    }

    public SyncReputationToClientS2CPacket(FriendlyByteBuf byteBuf) {
        reputation = byteBuf.readInt();
        reputationNeeded = byteBuf.readInt();
        reputationProgress = byteBuf.readFloat();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(reputation);
        byteBuf.writeInt(reputationNeeded);
        byteBuf.writeFloat(reputationProgress);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {

            ClientReputationData.set(reputation, reputationProgress, reputationNeeded);

        });
        return true;
    }

}