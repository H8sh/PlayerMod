package net.h8sh.playermod.networking.classes.rogue.doublee;

import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.h8sh.playermod.ability.rogue.doublee.DoubleCapabilityProvider;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TargetMarkCastC2SPacket {

    public TargetMarkCastC2SPacket() {
    }

    public TargetMarkCastC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            if (DoubleCapability.getReadyToUse()) {
                player.getCapability(DoubleCapabilityProvider.PLAYER_DOUBLE).ifPresent(doubleCapability -> {

                    player.sendSystemMessage(Component.literal("KABOOM"));

                    doubleCapability.spawnLeviathan(level);
                    DoubleCapability.teleportToTarget(DoubleCapability.getToTarget(), player);

                    DoubleCapability.setFrizzTarget(true);
                    DoubleCapability.setTargetMarkerOn(false);
                    DoubleCapability.setReadyToUse(false);
                    DoubleCapability.setOnCD(true);
                    KeyBinding.ULTIMATE_SPELL_KEY.setDown(false);
                });
            }

        });

        return true;
    }

}
