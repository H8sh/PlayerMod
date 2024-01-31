package net.h8sh.playermod.networking.camera;

import net.h8sh.playermod.capability.camera.CameraProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MoveCameraToRightC2SPacket {

    public MoveCameraToRightC2SPacket() {
    }

    public MoveCameraToRightC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            player.getCapability(CameraProvider.CAMERA).ifPresent(cameraManager -> {
                cameraManager.moveCameraToRight();
                player.sendSystemMessage(Component.literal("right"));
            });

        });

        return true;
    }

}
