package net.h8sh.playermod.networking.classes.wizard.mana;

import net.h8sh.playermod.ability.wizard.mana.crystal.ClientCrystalData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketSyncCrystalToClient {

    private final int crystal;

    public PacketSyncCrystalToClient(int crystal) {
        this.crystal = crystal;
    }

    public PacketSyncCrystalToClient(FriendlyByteBuf buf) {
        crystal = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(crystal);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ctx.enqueueWork(() -> {
            ClientCrystalData.setCrystal(crystal);
        });
        return true;
    }
}