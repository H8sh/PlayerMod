package net.h8sh.playermod.networking.profession;

import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ProfessionAquaMetaC2SPacket {

    public ProfessionAquaMetaC2SPacket() {
    }

    public ProfessionAquaMetaC2SPacket(FriendlyByteBuf byteBuf) {

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

                if (profession.getProfessionKnown() != 1) { // Check if there is already a profession
                    profession.addProfession(Profession.Professions.AQUAMETA);
                    player.sendSystemMessage(Component.literal("profession: "
                            + profession.getProfessionName()
                            + " and the profession id: " + profession.getProfession()).withStyle(ChatFormatting.RED));
                }
            });

        });

        return true;
    }

}
