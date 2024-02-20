package net.h8sh.playermod.networking.utils;

import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.animation.SyncDeltaMovementProgressS2CPacket;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class DashC2SPacket {

    public DashC2SPacket() {
    }

    public DashC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyRight.isDown() && !AnimationHandler.getPlayerRightDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(0), player);
                AnimationHandler.setPlayerRightDash(true);

            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyLeft.isDown() && !AnimationHandler.getPlayerLeftDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(1), player);
                AnimationHandler.setPlayerLeftDash(true);
            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyUp.isDown() && !AnimationHandler.getPlayerFrontDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(2), player);
                AnimationHandler.setPlayerFrontDash(true);
            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyDown.isDown() && !AnimationHandler.getPlayerBackDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(3), player);
                AnimationHandler.setPlayerBackDash(true);
            }
        });

        return true;
    }

}
