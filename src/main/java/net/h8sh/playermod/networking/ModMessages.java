package net.h8sh.playermod.networking;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.networking.classes.druid.metamorphose.*;
import net.h8sh.playermod.networking.classes.wizard.aoepacket.MagicAoES2CPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.GatherManaC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.SyncManaToClientS2CPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.crystal.KillAllCrystalS2CPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.crystal.ResetCrystalS2CPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.crystal.SyncCrystalToClientC2SPacket;
import net.h8sh.playermod.networking.narrator.*;
import net.h8sh.playermod.networking.profession.ProfessionDruidC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionPaladinC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionWizardC2SPacket;
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

        //Metamorphose ---------------------------------------------------------------------------------------------------

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

        // Metamorphose ------------------------------------------------------------------------------------------------------

        net.messageBuilder(RidingDruidC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RidingDruidC2SPacket::new)
                .encoder(RidingDruidC2SPacket::toBytes)
                .consumerMainThread(RidingDruidC2SPacket::handle)
                .add();

        net.messageBuilder(RidingPaladinC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RidingPaladinC2SPacket::new)
                .encoder(RidingPaladinC2SPacket::toBytes)
                .consumerMainThread(RidingPaladinC2SPacket::handle)
                .add();

        net.messageBuilder(RidingWizardC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RidingWizardC2SPacket::new)
                .encoder(RidingWizardC2SPacket::toBytes)
                .consumerMainThread(RidingWizardC2SPacket::handle)
                .add();

        net.messageBuilder(RidingResetC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RidingResetC2SPacket::new)
                .encoder(RidingResetC2SPacket::toBytes)
                .consumerMainThread(RidingResetC2SPacket::handle)
                .add();

        // Wizard ------------------------------------------------------------------------------------------------------

        //Mana
        net.messageBuilder(GatherManaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(GatherManaC2SPacket::new)
                .encoder(GatherManaC2SPacket::toBytes)
                .consumerMainThread(GatherManaC2SPacket::handle)
                .add();

        net.messageBuilder(SyncManaToClientS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncManaToClientS2CPacket::new)
                .encoder(SyncManaToClientS2CPacket::toBytes)
                .consumerMainThread(SyncManaToClientS2CPacket::handle)
                .add();

        //Crystal
        net.messageBuilder(SyncCrystalToClientC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncCrystalToClientC2SPacket::new)
                .encoder(SyncCrystalToClientC2SPacket::toBytes)
                .consumerMainThread(SyncCrystalToClientC2SPacket::handle)
                .add();

        net.messageBuilder(KillAllCrystalS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(KillAllCrystalS2CPacket::new)
                .encoder(KillAllCrystalS2CPacket::toBytes)
                .consumerMainThread(KillAllCrystalS2CPacket::handle)
                .add();

        net.messageBuilder(ResetCrystalS2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ResetCrystalS2CPacket::new)
                .encoder(ResetCrystalS2CPacket::toBytes)
                .consumerMainThread(ResetCrystalS2CPacket::handle)
                .add();

        //Magic AoE
        net.messageBuilder(MagicAoES2CPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MagicAoES2CPacket::new)
                .encoder(MagicAoES2CPacket::toBytes)
                .consumerMainThread(MagicAoES2CPacket::handle)
                .add();

        // Druid -------------------------------------------------------------------------------------------------------

        // Metamorphose
        net.messageBuilder(MetamorphoseAquaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MetamorphoseAquaC2SPacket::new)
                .encoder(MetamorphoseAquaC2SPacket::toBytes)
                .consumerMainThread(MetamorphoseAquaC2SPacket::handle)
                .add();

        net.messageBuilder(MetamorphoseFireC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MetamorphoseFireC2SPacket::new)
                .encoder(MetamorphoseFireC2SPacket::toBytes)
                .consumerMainThread(MetamorphoseFireC2SPacket::handle)
                .add();

        net.messageBuilder(MetamorphoseWindC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MetamorphoseWindC2SPacket::new)
                .encoder(MetamorphoseWindC2SPacket::toBytes)
                .consumerMainThread(MetamorphoseWindC2SPacket::handle)
                .add();

        net.messageBuilder(MetamorphoseSpiritusC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MetamorphoseSpiritusC2SPacket::new)
                .encoder(MetamorphoseSpiritusC2SPacket::toBytes)
                .consumerMainThread(MetamorphoseSpiritusC2SPacket::handle)
                .add();

        net.messageBuilder(MetamorphoseResetC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MetamorphoseResetC2SPacket::new)
                .encoder(MetamorphoseResetC2SPacket::toBytes)
                .consumerMainThread(MetamorphoseResetC2SPacket::handle)
                .add();

        // Mansion -----------------------------------------------------------------------------------------------------
        net.messageBuilder(OnChangedDimensionToMansionHuntedC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(OnChangedDimensionToMansionHuntedC2SPacket::new)
                .encoder(OnChangedDimensionToMansionHuntedC2SPacket::toBytes)
                .consumerMainThread(OnChangedDimensionToMansionHuntedC2SPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

}
