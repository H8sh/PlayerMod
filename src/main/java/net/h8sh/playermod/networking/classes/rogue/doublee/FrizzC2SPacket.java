package net.h8sh.playermod.networking.classes.rogue.doublee;

import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FrizzC2SPacket {

    public FrizzC2SPacket() {
    }

    public FrizzC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            DoubleCapability.frizz(DoubleCapability.getToTarget(),
                    DoubleCapability.getToTarget().getX(),
                    DoubleCapability.getToTarget().getY(),
                    DoubleCapability.getToTarget().getZ());

            player.sendSystemMessage(Component.literal("FRIZZ " + DoubleCapability.getToTarget().getName()));

        });

        return true;
    }

}
