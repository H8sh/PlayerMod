package net.h8sh.playermod.networking;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.networking.classes.wizard.AoEC2SPacket;
import net.h8sh.playermod.networking.narrator.*;
import net.h8sh.playermod.networking.profession.ProfessionBasicC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionDruidC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionPaladinC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionWizardC2SPacket;
import net.h8sh.playermod.networking.reputation.ReputationNormalC2SPacket;
import net.h8sh.playermod.networking.reputation.SyncReputationToClientS2CPacket;
import net.h8sh.playermod.networking.riding.RidingDruidC2SPacket;
import net.h8sh.playermod.networking.riding.RidingPaladinC2SPacket;
import net.h8sh.playermod.networking.riding.RidingResetC2SPacket;
import net.h8sh.playermod.networking.riding.RidingWizardC2SPacket;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToMansionHuntedC2SPacket;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToWonderlandsHomeC2SPacket;
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
                .named(new ResourceLocation(PlayerMod.MODID, "mod_messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        //Profession ---------------------------------------------------------------------------------------------------

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

        //Travelling ---------------------------------------------------------------------------------------------------

        net.messageBuilder(OnChangedDimensionToWonderlandsHomeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OnChangedDimensionToWonderlandsHomeC2SPacket::new)
                .encoder(OnChangedDimensionToWonderlandsHomeC2SPacket::toBytes)
                .consumerMainThread(OnChangedDimensionToWonderlandsHomeC2SPacket::handle)
                .add();

        //Narrator -----------------------------------------------------------------------------------------------------

        net.messageBuilder(NarratorSendSpawnMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendSpawnMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendSpawnMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendSpawnMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendWoodMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendWoodMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendWoodMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendWoodMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendEmptyMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendEmptyMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendEmptyMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendEmptyMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendCoalMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendCoalMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendCoalMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendCoalMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendSmeltingMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendSmeltingMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendSmeltingMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendSmeltingMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendDiamondMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendDiamondMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendDiamondMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendDiamondMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendNetherMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendNetherMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendNetherMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendNetherMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendEnderPearlMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendEnderPearlMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendEnderPearlMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendEnderPearlMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendEyeOfEnderPart1MessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendEyeOfEnderPart1MessageToPlayerC2SPacket::new)
                .encoder(NarratorSendEyeOfEnderPart1MessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendEyeOfEnderPart1MessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendWonderlandsMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendWonderlandsMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendWonderlandsMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendWonderlandsMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendEyeOfEnderPart2MessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendEyeOfEnderPart2MessageToPlayerC2SPacket::new)
                .encoder(NarratorSendEyeOfEnderPart2MessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendEyeOfEnderPart2MessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorSendEndMessageToPlayerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(NarratorSendEndMessageToPlayerC2SPacket::new)
                .encoder(NarratorSendEndMessageToPlayerC2SPacket::toBytes)
                .consumerMainThread(NarratorSendEndMessageToPlayerC2SPacket::handle)
                .add();

        net.messageBuilder(NarratorDataSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NarratorDataSyncS2CPacket::new)
                .encoder(NarratorDataSyncS2CPacket::toBytes)
                .consumerMainThread(NarratorDataSyncS2CPacket::handle)
                .add();

        // Riding ------------------------------------------------------------------------------------------------------

        net.messageBuilder(RidingDruidC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RidingDruidC2SPacket::new)
                .encoder(RidingDruidC2SPacket::toBytes)
                .consumerMainThread(RidingDruidC2SPacket::handle)
                .add();

        net.messageBuilder(RidingPaladinC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RidingPaladinC2SPacket::new)
                .encoder(RidingPaladinC2SPacket::toBytes)
                .consumerMainThread(RidingPaladinC2SPacket::handle)
                .add();

        net.messageBuilder(RidingWizardC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RidingWizardC2SPacket::new)
                .encoder(RidingWizardC2SPacket::toBytes)
                .consumerMainThread(RidingWizardC2SPacket::handle)
                .add();

        net.messageBuilder(RidingResetC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RidingResetC2SPacket::new)
                .encoder(RidingResetC2SPacket::toBytes)
                .consumerMainThread(RidingResetC2SPacket::handle)
                .add();

        // Wizard ------------------------------------------------------------------------------------------------------

        net.messageBuilder(AoEC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AoEC2SPacket::new)
                .encoder(AoEC2SPacket::toBytes)
                .consumerMainThread(AoEC2SPacket::handle)
                .add();

        // Druid -------------------------------------------------------------------------------------------------------

        //TODO

        // Mansion -----------------------------------------------------------------------------------------------------
        net.messageBuilder(OnChangedDimensionToMansionHuntedC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OnChangedDimensionToMansionHuntedC2SPacket::new)
                .encoder(OnChangedDimensionToMansionHuntedC2SPacket::toBytes)
                .consumerMainThread(OnChangedDimensionToMansionHuntedC2SPacket::handle)
                .add();

        //Reputation ---------------------------------------------------------------------------------------------------
        net.messageBuilder(SyncReputationToClientS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncReputationToClientS2CPacket::new)
                .encoder(SyncReputationToClientS2CPacket::toBytes)
                .consumerMainThread(SyncReputationToClientS2CPacket::handle)
                .add();

        net.messageBuilder(ReputationNormalC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ReputationNormalC2SPacket::new)
                .encoder(ReputationNormalC2SPacket::toBytes)
                .consumerMainThread(ReputationNormalC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
