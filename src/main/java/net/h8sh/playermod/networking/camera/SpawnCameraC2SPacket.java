package net.h8sh.playermod.networking.camera;

import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.event.PlayerEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SpawnCameraC2SPacket {

    public SpawnCameraC2SPacket() {
    }

    public SpawnCameraC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel serverLevel = player.serverLevel();

            if (PlayerEvent.getCamera() == null) {
                PlayerEvent.setCamera(ModEntities.CAMERA.get().create(serverLevel));
                serverLevel.addFreshEntity(PlayerEvent.getCamera());
                PlayerEvent.getCamera().setPos(player.getX(), player.getY(), player.getZ());
                PlayerEvent.getCamera().tame(player);
                player.sendSystemMessage(Component.literal("camera spawn"));
            }


        });

        return true;
    }

}
