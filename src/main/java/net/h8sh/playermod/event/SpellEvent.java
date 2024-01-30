package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.berserk.charge.ChargeCapability;
import net.h8sh.playermod.ability.berserk.rage.RageCapability;
import net.h8sh.playermod.ability.paladin.PaladinPassive;
import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.ability.wizard.mana.ManaCapability;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.gui.berserk.ChargeBarOverlay;
import net.h8sh.playermod.gui.berserk.RageBarOverlay;
import net.h8sh.playermod.gui.rogue.DoubleBarOverlay;
import net.h8sh.playermod.gui.wizard.AoEBarOverlay;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.druid.firemeta.fireaura.FireAuraC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.doublee.FrizzC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.doublee.TargetMarkCastC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.doublee.TargetMarkMarkerC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.aoe.AoECastC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.aoe.AoEMarkerC2SPacket;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class SpellEvent {

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide()) {
            return;
        }
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        ManaCapability.tick(event.level);

        if (Minecraft.getInstance().level != null) {
            int inGameSolarTime = (int) Minecraft.getInstance().level.getDayTime();
            int trueSolarTime = PaladinPassive.setCorrectTime(inGameSolarTime);
            PaladinPassive.setPaladinParameters(trueSolarTime);
            // time get from 0 to 24 000 so day from 0 to 12 000 and night from 12 000 to 24 000 modulo
        }

        //Gui: -----------------------------------------------------------------------------------------------------
        if (Profession.getProfession() == Profession.Professions.BASIC) {
            ClientEvents.setIsHotBar(true);
        }


        //Spells: --------------------------------------------------------------------------------------------------
        Player player = Minecraft.getInstance().player;
        if (player != null) {

            //Fire Aura: -------------------------------------------------------------------------------------------
            ModMessages.sendToServer(new FireAuraC2SPacket());

            //AoE: -------------------------------------------------------------------------------------------------
            ModMessages.sendToServer(new AoEMarkerC2SPacket());
            ModMessages.sendToServer(new AoECastC2SPacket());

            //Rogue Ultimate: --------------------------------------------------------------------------------------
            ModMessages.sendToServer(new TargetMarkMarkerC2SPacket());
            ModMessages.sendToServer(new TargetMarkCastC2SPacket());
            if (DoubleCapability.isTargetMarkerOn()) {
                DoubleCapability.spawnTargetMarkMarker(player);
            }
            if (DoubleCapability.isEntityFrizz()) {
                ModMessages.sendToServer(new FrizzC2SPacket());
            }
        }
    }

    @SubscribeEvent
    public static void onSpellCastGui(RenderGuiOverlayEvent event) {
        if (event.getOverlay().overlay() == AoEBarOverlay.HUD_AOE_BAR_PROGRESS) {
            event.setCanceled(true);
            if (KeyBinding.SECOND_SPELL_KEY.isDown() && Profession.getProfession() == Profession.Professions.WIZARD && AoEBarOverlay.getCurrentProgress() != AoEBarOverlay.getCurrentProgressD() && !MagicAoECapability.isOnCD()) {
                event.getOverlay().overlay().render(
                        new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                        event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
            }
            if (!KeyBinding.SECOND_SPELL_KEY.isDown()) {
                AoEBarOverlay.setCurrentProgress(0.0F);
            }
        }
        if (event.getOverlay().overlay() == DoubleBarOverlay.HUD_DOUBLE_BAR_PROGRESS) {
            event.setCanceled(true);
            if (KeyBinding.ULTIMATE_SPELL_KEY.isDown() && Profession.getProfession() == Profession.Professions.ROGUE && DoubleBarOverlay.getCurrentProgress() != DoubleBarOverlay.getCurrentProgressD() && !DoubleCapability.isOnCD()) {
                event.getOverlay().overlay().render(
                        new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                        event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
            }
            if (!KeyBinding.ULTIMATE_SPELL_KEY.isDown()) {
                DoubleBarOverlay.setCurrentProgress(0.0F);
            }
        }
        if (event.getOverlay().overlay() == ChargeBarOverlay.HUD_CHARGE_BAR_PROGRESS) {
            event.setCanceled(true);
            if (Profession.getProfession() == Profession.Professions.BERSERK && ChargeBarOverlay.getCurrentProgress() != ChargeBarOverlay.getCurrentProgressD() && ChargeCapability.getOnCharge()) {
                event.getOverlay().overlay().render(
                        new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                        event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
            }
            if (!ChargeCapability.getOnCharge()) {
                ChargeBarOverlay.setCurrentProgress(0.0F);
            }
        }
        if (event.getOverlay().overlay() == RageBarOverlay.HUD_RAGE_BAR_PROGRESS) {
            event.setCanceled(true);
            if (Profession.getProfession() == Profession.Professions.BERSERK && RageBarOverlay.getCurrentProgress() != RageBarOverlay.getCurrentProgressD() && RageCapability.getOnRage()) {
                event.getOverlay().overlay().render(
                        new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                        event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
            }
            if (!RageCapability.getOnRage()) {
                RageBarOverlay.setCurrentProgress(0.0F);
            }
        }
    }

}
