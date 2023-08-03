package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.gui.CrystalOverlay;
import net.h8sh.playermod.gui.ManaBarOverlay;
import net.h8sh.playermod.gui.ManaOverlay;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.profession.ProfessionDruidC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionPaladinC2SPacket;
import net.h8sh.playermod.networking.profession.ProfessionWizardC2SPacket;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class ProfessionEvent {

    @SubscribeEvent
    public static void onPlayerGettingProfessionBook(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getEntity();

        if (event.getStack().is(ModItems.PALADIN_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.PALADIN) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionPaladinC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.WIZARD_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.WIZARD) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionWizardC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.DRUID_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.DRUID) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionDruidC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onGuiRender(RenderGuiOverlayEvent event) {
        Player player = Minecraft.getInstance().player;
        player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
            var currentProfession = Profession.getProfession();

            //TODO: correct overlay switch

            switch (currentProfession) {
                case PALADIN:
                    if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
                        event.setCanceled(true);
                    }

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }

                case WIZARD:
                    if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
                        event.setCanceled(true);
                    }

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }

                case DRUID:
                    if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
                        event.setCanceled(true);
                    }

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                case BASIC: //Vanilla

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }

            }
        });

    }

}
