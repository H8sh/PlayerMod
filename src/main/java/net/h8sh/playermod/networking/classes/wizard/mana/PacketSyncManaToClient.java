package net.h8sh.playermod.networking.classes.wizard.mana;

import net.h8sh.playermod.ability.wizard.laser.LaserCapability;
import net.h8sh.playermod.ability.wizard.laser.LaserCapabilityProvider;
import net.h8sh.playermod.ability.wizard.mana.ClientManaData;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncManaToClient {

    private final int playerMana;
    private final int chunkMana;

    public PacketSyncManaToClient(int playerMana, int chunkMana) {
        this.playerMana = playerMana;
        this.chunkMana = chunkMana;
    }

    public PacketSyncManaToClient(FriendlyByteBuf buf) {
        playerMana = buf.readInt();
        chunkMana = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(playerMana);
        buf.writeInt(chunkMana);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientManaData.setChunkMana(chunkMana);
            ClientManaData.setPlayerMana(playerMana);
        });
        return true;
    }
}