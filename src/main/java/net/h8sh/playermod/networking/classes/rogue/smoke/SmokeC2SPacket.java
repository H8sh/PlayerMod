package net.h8sh.playermod.networking.classes.rogue.smoke;

import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapabilityProvider;
import net.h8sh.playermod.effect.ModEffects;
import net.h8sh.playermod.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SmokeC2SPacket {

    public SmokeC2SPacket() {
    }

    public SmokeC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!SmokeCapability.getOnSmoke()) {

                player.getCapability(SmokeCapabilityProvider.PLAYER_SMOKE).ifPresent(smokeCapability -> {
                    SmokeCapability.setOnSmoke(true);
                    player.addEffect(new MobEffectInstance(ModEffects.SMOKE.get(), 20, 0, false, true, false));
                    player.playSound(ModSounds.ROGUE_LAUGH.get(), 1, 1);
                });
            }
        });

        return true;
    }

}
