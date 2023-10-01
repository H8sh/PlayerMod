package net.h8sh.playermod.networking.classes.wizard.laser;

import net.h8sh.playermod.ability.wizard.laser.LaserCapability;
import net.h8sh.playermod.ability.wizard.laser.LaserCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class LaserC2SPacket {

    public LaserC2SPacket() {
    }

    public LaserC2SPacket(FriendlyByteBuf byteBuf) {    

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!LaserCapability.getOnLaserActivated()) {

                player.getCapability(LaserCapabilityProvider.PLAYER_LASER).ifPresent(laserCapability -> {

                    player.sendSystemMessage(Component.literal("LASER").withStyle(ChatFormatting.AQUA));
                    LaserCapability.setOnLaserActivated(true);


                });
            }
        });

        return true;
    }

}
