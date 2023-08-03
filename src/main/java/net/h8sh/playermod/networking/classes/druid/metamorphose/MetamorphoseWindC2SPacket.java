package net.h8sh.playermod.networking.classes.druid.metamorphose;

import net.h8sh.playermod.capability.ability.druid.metamorphose.Metamorphose;
import net.h8sh.playermod.capability.ability.druid.metamorphose.MetamorphoseProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MetamorphoseWindC2SPacket {

    public MetamorphoseWindC2SPacket() {
    }

    public MetamorphoseWindC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();


            level.playSound(null, player.getOnPos(), SoundEvents.TNT_PRIMED, SoundSource.PLAYERS,
                    0.5F, level.random.nextFloat() * 1.0F + 0.9F);

            player.getCapability(MetamorphoseProvider.METAMORPHOSE).ifPresent(metamorphose -> {

                if (metamorphose.getMetamorphoseKnown() != 1) { // Check if there is already a mount
                    metamorphose.addMetamorphose(Metamorphose.Metamorphoses.WIND);
                    player.sendSystemMessage(Component.literal("ridings: "
                            + metamorphose.getMetamorphoseKnown()
                            + " and the riding id: " + metamorphose.getMetamorphose()).withStyle(ChatFormatting.RED));
                }
            });

        });

        return true;
    }

}
