package net.h8sh.playermod.networking.classes.rogue.teleportation;

import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapabilityProvider;
import net.h8sh.playermod.ability.rogue.teleportation.TeleportationCapability;
import net.h8sh.playermod.ability.rogue.teleportation.TeleportationCapabilityProvider;
import net.h8sh.playermod.effect.ModEffects;
import net.h8sh.playermod.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TeleportationC2SPacket {

    public TeleportationC2SPacket() {
    }

    public TeleportationC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!TeleportationCapability.getIsTeleporting()) {

                player.getCapability(TeleportationCapabilityProvider.PLAYER_TELEPORTATION).ifPresent(teleportation -> {
                    TeleportationCapability.teleport(player);
                    TeleportationCapability.setIsTeleporting(true);
                });
            }
        });

        return true;
    }

}
