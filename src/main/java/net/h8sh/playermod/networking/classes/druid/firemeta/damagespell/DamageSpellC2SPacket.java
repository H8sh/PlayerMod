package net.h8sh.playermod.networking.classes.druid.firemeta.damagespell;

import net.h8sh.playermod.ability.druid.firemeta.damage_spell.DamageSpellCapability;
import net.h8sh.playermod.ability.druid.firemeta.damage_spell.DamageSpellCapabilityProvider;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapabilityProvider;
import net.h8sh.playermod.effect.ModEffects;
import net.h8sh.playermod.sound.ModSounds;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DamageSpellC2SPacket {

    public DamageSpellC2SPacket() {
    }

    public DamageSpellC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            if (!DamageSpellCapability.getOnDamageSpell()) {

                player.getCapability(DamageSpellCapabilityProvider.PLAYER_DAMAGESPELL).ifPresent(damageSpellCapability -> {
                    DamageSpellCapability.selectEntitiesToBump(level, player);
                    DamageSpellCapability.setOnDamageSpell(true);
                });
            }
        });

        return true;
    }

}
