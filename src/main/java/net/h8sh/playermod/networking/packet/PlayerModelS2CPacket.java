package net.h8sh.playermod.networking.packet;

import net.h8sh.playermod.capability.profession.ProfessionData;
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

public class PlayerModelS2CPacket {

    private final int profession;
    public PlayerModelS2CPacket(int profession) {
        this.profession = profession;
    }

    public PlayerModelS2CPacket(FriendlyByteBuf byteBuf) {
        this.profession = byteBuf.readInt();
    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(profession);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side

            ProfessionData.set(profession);

        });

        return true;
    }

}
