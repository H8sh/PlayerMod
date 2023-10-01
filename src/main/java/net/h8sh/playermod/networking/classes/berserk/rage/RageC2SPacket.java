package net.h8sh.playermod.networking.classes.berserk.rage;

import net.h8sh.playermod.ability.berserk.charge.ChargeCapability;
import net.h8sh.playermod.ability.berserk.rage.RageCapability;
import net.h8sh.playermod.ability.berserk.rage.RageCapabilityProvider;
import net.h8sh.playermod.gui.berserk.RageBarOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RageC2SPacket {

    public RageC2SPacket() {
    }

    public RageC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!RageCapability.getOnRage()) {
                player.getCapability(RageCapabilityProvider.PLAYER_RAGE).ifPresent(rage -> {
                    RageCapability.setOnRage(true);
                    rage.rage();
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0, false,
                            true, false));
                });
            }
        });

        return true;
    }

}
