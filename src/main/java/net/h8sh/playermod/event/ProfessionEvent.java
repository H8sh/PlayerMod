package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.packet.profession.ProfessionDruidC2SPacket;
import net.h8sh.playermod.networking.packet.profession.ProfessionPaladinC2SPacket;
import net.h8sh.playermod.networking.packet.profession.ProfessionWizardC2SPacket;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class ProfessionEvent {

    @SubscribeEvent
    public static void onPlayerGettingProfessionBook(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getEntity();
        if (event.getStack().is(ModItems.DRUID_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.DRUID.getId()) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionDruidC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.PALADIN_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.PALADIN.getId()) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionPaladinC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.WIZARD_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.WIZARD.getId()) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionWizardC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onGuiRender(RenderGuiOverlayEvent event) {
        Player player = Minecraft.getInstance().player;
        player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
            switch (profession.getProfession()) {
                case 1:
                    if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
                        event.setCanceled(true);
                    }
                case 2:
                    if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
                        event.setCanceled(true);
                    }
                case 3:
                    if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
                        event.setCanceled(true);
                    }
            }
        });

    }

}
