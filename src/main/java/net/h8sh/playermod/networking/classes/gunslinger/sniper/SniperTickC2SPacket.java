package net.h8sh.playermod.networking.classes.gunslinger.sniper;

import net.h8sh.playermod.ability.gunslinger.sniper.SniperCapabilityProvider;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapabilityProvider;
import net.h8sh.playermod.effect.ModEffects;
import net.h8sh.playermod.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SniperTickC2SPacket {

    public SniperTickC2SPacket() {
    }

    public SniperTickC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel serverLevel = player.serverLevel();

            player.getCapability(SniperCapabilityProvider.PLAYER_SNIPER).ifPresent(sniperCapability -> {

                //TODO

            });

        });

        return true;
    }

}
