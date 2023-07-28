package net.h8sh.playermod.ability.networking.wizard.manapacket;

import net.h8sh.playermod.ability.wizard.mana.ClientManaData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncManaToClient {

    private final int playerMana;
    private final int chunkMana;

    public PacketSyncManaToClient(int playerMana, int chunkMana) {
        this.playerMana = playerMana;
        this.chunkMana = chunkMana;
    }

    public PacketSyncManaToClient(FriendlyByteBuf byteBuf) {
        playerMana = byteBuf.readInt();
        chunkMana = byteBuf.readInt();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(playerMana);
        byteBuf.writeInt(chunkMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //server side

            Player player = context.getSender();

            player.getBoundingBox().setMinX(0);
            player.getBoundingBox().setMinY(0);
            player.getBoundingBox().setMinZ(0);
            player.getBoundingBox().setMaxX(0);
            player.getBoundingBox().setMaxY(0);
            player.getBoundingBox().setMaxZ(0);

            ClientManaData.set(playerMana, chunkMana);

        });
        return true;
    }

}