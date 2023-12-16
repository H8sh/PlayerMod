package net.h8sh.playermod.networking;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.networking.animation.SteveAttackC2SPacket;
import net.h8sh.playermod.networking.animation.SteveShiftDownC2SPacket;
import net.h8sh.playermod.networking.animation.SyncDeltaMovementProgressS2CPacket;
import net.h8sh.playermod.networking.classes.berserk.charge.ChargeC2SPacket;
import net.h8sh.playermod.networking.classes.berserk.SyncRageBarProgressS2CPacket;
import net.h8sh.playermod.networking.classes.berserk.healthsacrifice.HealthSacrificeC2SPacket;
import net.h8sh.playermod.networking.classes.berserk.rage.RageC2SPacket;
import net.h8sh.playermod.networking.classes.berserk.slam.SlamC2SPacket;
import net.h8sh.playermod.networking.classes.druid.firemeta.damagespell.DamageSpellC2SPacket;
import net.h8sh.playermod.networking.classes.druid.firemeta.fireaura.FireAuraC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.doublee.FrizzC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.doublee.TargetMarkCastC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.doublee.TargetMarkMarkerC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.smoke.SmokeC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.teleportation.TeleportationC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.aoe.AoECastC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.aoe.AoEMarkerC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.laser.LaserC2SPacket;
import net.h8sh.playermod.networking.narrator.*;
import net.h8sh.playermod.networking.profession.*;
import net.h8sh.playermod.networking.reputation.ReputationNormalC2SPacket;
import net.h8sh.playermod.networking.reputation.SyncReputationToClientS2CPacket;
import net.h8sh.playermod.networking.riding.RidingDruidC2SPacket;
import net.h8sh.playermod.networking.riding.RidingPaladinC2SPacket;
import net.h8sh.playermod.networking.riding.RidingResetC2SPacket;
import net.h8sh.playermod.networking.riding.RidingWizardC2SPacket;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToMansionHuntedC2SPacket;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToWonderlandsHomeC2SPacket;
import net.h8sh.playermod.networking.utils.DashC2SPacket;
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

        //Animations ---------------------------------------------------------------------------------------------------
        net.messageBuilder(SteveAttackC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SteveAttackC2SPacket::new)
                .encoder(SteveAttackC2SPacket::toBytes)
                .consumerMainThread(SteveAttackC2SPacket::handle)
                .add();

        net.messageBuilder(SteveShiftDownC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SteveShiftDownC2SPacket::new)
                .encoder(SteveShiftDownC2SPacket::toBytes)
                .consumerMainThread(SteveShiftDownC2SPacket::handle)
                .add();

        net.messageBuilder(SyncDeltaMovementProgressS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncDeltaMovementProgressS2CPacket::new)
                .encoder(SyncDeltaMovementProgressS2CPacket::toBytes)
                .consumerMainThread(SyncDeltaMovementProgressS2CPacket::handle)
                .add();

        //Utils --------------------------------------------------------------------------------------------------------

        net.messageBuilder(DashC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DashC2SPacket::new)
                .encoder(DashC2SPacket::toBytes)
                .consumerMainThread(DashC2SPacket::handle)
                .add();


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

        net.messageBuilder(ProfessionRogueC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionRogueC2SPacket::new)
                .encoder(ProfessionRogueC2SPacket::toBytes)
                .consumerMainThread(ProfessionRogueC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionBerserkC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionBerserkC2SPacket::new)
                .encoder(ProfessionBerserkC2SPacket::toBytes)
                .consumerMainThread(ProfessionBerserkC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionInvocatorC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionInvocatorC2SPacket::new)
                .encoder(ProfessionInvocatorC2SPacket::toBytes)
                .consumerMainThread(ProfessionInvocatorC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionFireMetaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionFireMetaC2SPacket::new)
                .encoder(ProfessionFireMetaC2SPacket::toBytes)
                .consumerMainThread(ProfessionFireMetaC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionAquaMetaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionAquaMetaC2SPacket::new)
                .encoder(ProfessionAquaMetaC2SPacket::toBytes)
                .consumerMainThread(ProfessionAquaMetaC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionWindMetaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionWindMetaC2SPacket::new)
                .encoder(ProfessionWindMetaC2SPacket::toBytes)
                .consumerMainThread(ProfessionWindMetaC2SPacket::handle)
                .add();

        net.messageBuilder(ProfessionSpiritusMetaC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ProfessionSpiritusMetaC2SPacket::new)
                .encoder(ProfessionSpiritusMetaC2SPacket::toBytes)
                .consumerMainThread(ProfessionSpiritusMetaC2SPacket::handle)
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

        net.messageBuilder(AoEMarkerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AoEMarkerC2SPacket::new)
                .encoder(AoEMarkerC2SPacket::toBytes)
                .consumerMainThread(AoEMarkerC2SPacket::handle)
                .add();

        net.messageBuilder(AoECastC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AoECastC2SPacket::new)
                .encoder(AoECastC2SPacket::toBytes)
                .consumerMainThread(AoECastC2SPacket::handle)
                .add();

        net.messageBuilder(LaserC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(LaserC2SPacket::new)
                .encoder(LaserC2SPacket::toBytes)
                .consumerMainThread(LaserC2SPacket::handle)
                .add();

        // Berserk -----------------------------------------------------------------------------------------------------

        net.messageBuilder(SyncRageBarProgressS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncRageBarProgressS2CPacket::new)
                .encoder(SyncRageBarProgressS2CPacket::toBytes)
                .consumerMainThread(SyncRageBarProgressS2CPacket::handle)
                .add();

        net.messageBuilder(HealthSacrificeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(HealthSacrificeC2SPacket::new)
                .encoder(HealthSacrificeC2SPacket::toBytes)
                .consumerMainThread(HealthSacrificeC2SPacket::handle)
                .add();

        net.messageBuilder(SlamC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SlamC2SPacket::new)
                .encoder(SlamC2SPacket::toBytes)
                .consumerMainThread(SlamC2SPacket::handle)
                .add();

        net.messageBuilder(ChargeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChargeC2SPacket::new)
                .encoder(ChargeC2SPacket::toBytes)
                .consumerMainThread(ChargeC2SPacket::handle)
                .add();

        net.messageBuilder(RageC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(RageC2SPacket::new)
                .encoder(RageC2SPacket::toBytes)
                .consumerMainThread(RageC2SPacket::handle)
                .add();

        // Rogue -------------------------------------------------------------------------------------------------------

        net.messageBuilder(SmokeC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SmokeC2SPacket::new)
                .encoder(SmokeC2SPacket::toBytes)
                .consumerMainThread(SmokeC2SPacket::handle)
                .add();

        net.messageBuilder(TeleportationC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TeleportationC2SPacket::new)
                .encoder(TeleportationC2SPacket::toBytes)
                .consumerMainThread(TeleportationC2SPacket::handle)
                .add();

        net.messageBuilder(TargetMarkMarkerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TargetMarkMarkerC2SPacket::new)
                .encoder(TargetMarkMarkerC2SPacket::toBytes)
                .consumerMainThread(TargetMarkMarkerC2SPacket::handle)
                .add();

        net.messageBuilder(TargetMarkCastC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TargetMarkCastC2SPacket::new)
                .encoder(TargetMarkCastC2SPacket::toBytes)
                .consumerMainThread(TargetMarkCastC2SPacket::handle)
                .add();

        net.messageBuilder(FrizzC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FrizzC2SPacket::new)
                .encoder(FrizzC2SPacket::toBytes)
                .consumerMainThread(FrizzC2SPacket::handle)
                .add();

        // FIREMETA ----------------------------------------------------------------------------------------------------

        net.messageBuilder(DamageSpellC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(DamageSpellC2SPacket::new)
                .encoder(DamageSpellC2SPacket::toBytes)
                .consumerMainThread(DamageSpellC2SPacket::handle)
                .add();

        net.messageBuilder(FireAuraC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(FireAuraC2SPacket::new)
                .encoder(FireAuraC2SPacket::toBytes)
                .consumerMainThread(FireAuraC2SPacket::handle)
                .add();

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
