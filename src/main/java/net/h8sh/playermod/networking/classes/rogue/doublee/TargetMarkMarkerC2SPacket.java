package net.h8sh.playermod.networking.classes.rogue.doublee;

import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.h8sh.playermod.ability.rogue.doublee.DoubleCapabilityProvider;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.gui.rogue.DoubleBarOverlay;
import net.h8sh.playermod.gui.wizard.AoEBarOverlay;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TargetMarkMarkerC2SPacket {

    public TargetMarkMarkerC2SPacket() {
    }

    public TargetMarkMarkerC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (DoubleCapability.getPrepareTargetMark() && !DoubleCapability.isOnCD()) {
                player.getCapability(DoubleCapabilityProvider.PLAYER_DOUBLE).ifPresent(doubleCapability -> {

                    DoubleCapability.setTargetMarkerOn(true);

                    if (!KeyBinding.ULTIMATE_SPELL_KEY.isDown() && !DoubleCapability.getReadyToUse()) {
                        DoubleCapability.setPrepareTargetMark(false);
                        DoubleCapability.setTargetMarkerOn(false);
                    }

                    if (DoubleBarOverlay.getCurrentProgress() == DoubleBarOverlay.getCurrentProgressD()) {
                        DoubleCapability.setReadyToUse(true);
                        DoubleCapability.setPrepareTargetMark(false);
                    }

                });
            }

        });

        return true;
    }

}
