package net.h8sh.playermod.networking.classes.berserk.healthsacrifice;

import net.h8sh.playermod.ability.berserk.healthsacrifice.HealthSacrificeCapability;
import net.h8sh.playermod.ability.berserk.healthsacrifice.HealthSacrificeCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HealthSacrificeC2SPacket {

    public HealthSacrificeC2SPacket() {
    }

    public HealthSacrificeC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (!HealthSacrificeCapability.getOnHealthSacrifice()) {
                player.getCapability(HealthSacrificeCapabilityProvider.PLAYER_HEALTHSACRIFICE).ifPresent(healthSacrificeCapability -> {
                    HealthSacrificeCapability.setOnHealthSacrifice(true);
                    player.setHealth(player.getMaxHealth() * 0.3F);
                    player.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(player.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() * 2);
                });
            }
        });

        return true;
    }

}
