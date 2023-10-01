package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.berserk.charge.ChargeCapability;
import net.h8sh.playermod.ability.berserk.rage.RageCapability;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.gui.berserk.RageBarOverlay;
import net.h8sh.playermod.gui.wizard.AoEBarOverlay;
import net.h8sh.playermod.gui.berserk.ChargeBarOverlay;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.berserk.rage.RageC2SPacket;
import net.h8sh.playermod.networking.classes.druid.firemeta.fireaura.FireAuraC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.aoe.AoECastC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.aoe.AoEMarkerC2SPacket;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
