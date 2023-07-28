package net.h8sh.playermod.ability.networking;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.networking.wizard.aoepacket.PacketMagicAoE;
import net.h8sh.playermod.ability.networking.wizard.manapacket.PacketGatherMana;
import net.h8sh.playermod.ability.networking.wizard.manapacket.PacketSyncManaToClient;
import net.h8sh.playermod.ability.networking.wizard.manapacket.crystal.PacketKillAllCrystal;
import net.h8sh.playermod.ability.networking.wizard.manapacket.crystal.PacketResetCrystal;
import net.h8sh.playermod.ability.networking.wizard.manapacket.crystal.PacketSyncCrystalToClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class AbilityMessages {

    public static int packetId = 0;
    private static SimpleChannel INSTANCE;

    public static int id() {
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

        //-----------------------------------------------------------------------------------------------------

        //Mana
        net.messageBuilder(PacketGatherMana.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketGatherMana::new)
                .encoder(PacketGatherMana::toBytes)
                .consumerMainThread(PacketGatherMana::handle)
                .add();

        net.messageBuilder(PacketSyncManaToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncManaToClient::new)
                .encoder(PacketSyncManaToClient::toBytes)
                .consumerMainThread(PacketSyncManaToClient::handle)
                .add();

        //Crystal
        net.messageBuilder(PacketSyncCrystalToClient.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PacketSyncCrystalToClient::new)
                .encoder(PacketSyncCrystalToClient::toBytes)
                .consumerMainThread(PacketSyncCrystalToClient::handle)
                .add();

        net.messageBuilder(PacketKillAllCrystal.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketKillAllCrystal::new)
                .encoder(PacketKillAllCrystal::toBytes)
                .consumerMainThread(PacketKillAllCrystal::handle)
                .add();

        net.messageBuilder(PacketResetCrystal.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketResetCrystal::new)
                .encoder(PacketResetCrystal::toBytes)
                .consumerMainThread(PacketResetCrystal::handle)
                .add();

        //Magic AoE
        net.messageBuilder(PacketMagicAoE.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketMagicAoE::new)
                .encoder(PacketMagicAoE::toBytes)
                .consumerMainThread(PacketMagicAoE::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
