package net.h8sh.playermod.networking.riding;

import net.h8sh.playermod.capability.riding.Riding;
import net.h8sh.playermod.capability.riding.RidingProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RidingPaladinC2SPacket {

    public RidingPaladinC2SPacket() {
    }

    public RidingPaladinC2SPacket(FriendlyByteBuf byteBuf) {

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

            player.getCapability(RidingProvider.RIDING).ifPresent(riding -> {

                if (riding.getRidingKnown() != 1) { // Check if there is already a mount
                    riding.addRiding(Riding.Ridings.PALADIN_MOUNT);
                    player.sendSystemMessage(Component.literal("ridings: "
                            + riding.getRidingKnown()
                            + " and the riding id: " + riding.getRiding()).withStyle(ChatFormatting.RED));
                }
            });

        });

        return true;
    }

}
