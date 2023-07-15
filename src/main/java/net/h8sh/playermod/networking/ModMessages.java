package net.h8sh.playermod.networking;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.networking.packet.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static SimpleChannel INSTANCE;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(PlayerMod.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(ProfessionDruidC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionDruidC2SPacket::new)
                .encoder(ProfessionDruidC2SPacket::toBytes)
                .consumerMainThread(ProfessionDruidC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionPaladinC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionPaladinC2SPacket::new)
                .encoder(ProfessionPaladinC2SPacket::toBytes)
                .consumerMainThread(ProfessionPaladinC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionWizardC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionWizardC2SPacket::new)
                .encoder(ProfessionWizardC2SPacket::toBytes)
                .consumerMainThread(ProfessionWizardC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionBasicC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionBasicC2SPacket::new)
                .encoder(ProfessionBasicC2SPacket::toBytes)
                .consumerMainThread(ProfessionBasicC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
    }

}
