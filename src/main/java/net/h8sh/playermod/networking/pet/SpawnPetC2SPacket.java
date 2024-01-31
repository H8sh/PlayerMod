package net.h8sh.playermod.networking.pet;

import net.h8sh.playermod.capability.pet.PetProvider;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.event.PlayerEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnPetC2SPacket {

    public SpawnPetC2SPacket() {
    }

    public SpawnPetC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel serverLevel = player.serverLevel();

            player.getCapability(PetProvider.PET).ifPresent(pet -> {
                if (!pet.hasPet()) {
                    PlayerEvent.setPet(ModEntities.PET.get().create(serverLevel));
                    serverLevel.addFreshEntity(PlayerEvent.getPet());
                    PlayerEvent.getPet().setPos(player.getX(), player.getY(), player.getZ());
                    PlayerEvent.getPet().tame(player);
                    player.sendSystemMessage(Component.literal("camera spawn"));
                    pet.setPet(true);
                } else {
                    player.sendSystemMessage(Component.literal("camera not spawn"));
                }
            });


        });

        return true;
    }

}
