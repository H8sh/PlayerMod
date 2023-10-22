package net.h8sh.playermod.networking.utils;

import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.animation.SyncDeltaMovementProgressS2CPacket;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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

            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyRight.isDown() && !AnimationHandler.getSteveRightDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(0), player);
                AnimationHandler.setSteveRightDash(true);

            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyLeft.isDown() && !AnimationHandler.getSteveLeftDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(1), player);
                AnimationHandler.setSteveLeftDash(true);
            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyUp.isDown() && !AnimationHandler.getSteveFrontDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(2), player);
                AnimationHandler.setSteveFrontDash(true);
            }
            if (KeyBinding.DASH_KEY.isDown() && Minecraft.getInstance().options.keyDown.isDown() && !AnimationHandler.getSteveBackDash()) {
                ModMessages.sendToPlayer(new SyncDeltaMovementProgressS2CPacket(3), player);
                AnimationHandler.setSteveBackDash(true);
            }
        });

        return true;
    }

}
