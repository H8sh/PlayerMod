package net.h8sh.playermod.networking.packet;

import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.networking.ModMessages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProfessionCurrentC2SPacket {

    public ProfessionCurrentC2SPacket() {
    }

    public ProfessionCurrentC2SPacket(FriendlyByteBuf byteBuf) {

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

            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {


                player.sendSystemMessage(Component.literal("profession: "
                        + profession.getProfessionByName()
                        + " and the number of professions known are: " + profession.getProfessionKnown()).withStyle(ChatFormatting.GREEN));
                ModMessages.sendToPlayer(new PlayerModelS2CPacket(profession.getProfession()), player);
            });

        });

        return true;
    }

}
