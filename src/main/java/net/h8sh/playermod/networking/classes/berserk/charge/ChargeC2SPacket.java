package net.h8sh.playermod.networking.classes.berserk.charge;

import net.h8sh.playermod.ability.berserk.charge.ChargeCapability;
import net.h8sh.playermod.ability.berserk.charge.ChargeCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChargeC2SPacket {

    public ChargeC2SPacket() {
    }

    public ChargeC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!ChargeCapability.getOnCharge()) {
                player.getCapability(ChargeCapabilityProvider.PLAYER_CHARGE).ifPresent(charge -> {
                    ChargeCapability.setOnCharge(true);
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0, false, true, false));
                });
            }
        });

        return true;
    }

}
