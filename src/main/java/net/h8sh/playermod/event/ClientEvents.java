package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.narrator.NarratorOverlay;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.packet.profession.ProfessionBasicC2SPacket;
import net.h8sh.playermod.networking.packet.profession.ProfessionDruidC2SPacket;
import net.h8sh.playermod.networking.packet.profession.ProfessionPaladinC2SPacket;
import net.h8sh.playermod.networking.packet.profession.ProfessionWizardC2SPacket;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.DRUID_PROFESSION_KEY.consumeClick()) {
                ModMessages.sendToServer(new ProfessionDruidC2SPacket());
            }
            if (KeyBinding.PALADIN_PROFESSION_KEY.consumeClick()) {
                ModMessages.sendToServer(new ProfessionPaladinC2SPacket());
            }
            if (KeyBinding.WIZARD_PROFESSION_KEY.consumeClick()) {
                ModMessages.sendToServer(new ProfessionWizardC2SPacket());
            }
            if (KeyBinding.BASIC_PROFESSION_KEY.consumeClick()) {
                ModMessages.sendToServer(new ProfessionBasicC2SPacket());
            }

        }

    }

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvent {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DRUID_PROFESSION_KEY);
            event.register(KeyBinding.PALADIN_PROFESSION_KEY);
            event.register(KeyBinding.WIZARD_PROFESSION_KEY);
            event.register(KeyBinding.BASIC_PROFESSION_KEY);
            event.register(KeyBinding.CURRENT_PROFESSION_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("narrator", NarratorOverlay.HUD_THIRST);
        }

    }
}
