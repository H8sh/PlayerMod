package net.h8sh.playermod.networking.classes.rogue.doublee;

import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.h8sh.playermod.effect.ModEffects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FrizzC2SPacket {

    public FrizzC2SPacket() {
    }

    public FrizzC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (DoubleCapability.getToTarget() instanceof LivingEntity entity) {
                entity.addEffect(new MobEffectInstance(ModEffects.SMOKE.get(), 20, 0, false, true, false));

                player.sendSystemMessage(Component.literal("FRIZZ "));
            }

        });

        return true;
    }

}
