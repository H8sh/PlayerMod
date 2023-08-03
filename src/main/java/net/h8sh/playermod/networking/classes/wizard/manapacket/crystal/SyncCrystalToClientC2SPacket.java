package net.h8sh.playermod.networking.classes.wizard.manapacket.crystal;

import net.h8sh.playermod.ability.wizard.mana.crystal.ClientCrystalData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncCrystalToClientC2SPacket {

    private final int playerCrystal;


    public SyncCrystalToClientC2SPacket(int playerCrystal) {

        this.playerCrystal = playerCrystal;
    }

    public SyncCrystalToClientC2SPacket(FriendlyByteBuf byteBuf) {

        playerCrystal = byteBuf.readInt();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {

        byteBuf.writeInt(playerCrystal);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //server side

            ClientCrystalData.set(playerCrystal);

        });
        return true;
    }

}