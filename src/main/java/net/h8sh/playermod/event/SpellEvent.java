package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.gui.AoEBarOverlay;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class SpellEvent {

    @SubscribeEvent
    public static void onSpellCast(RenderGuiOverlayEvent event) {
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
    }

}
