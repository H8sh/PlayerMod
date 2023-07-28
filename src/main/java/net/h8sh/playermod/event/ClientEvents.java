package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.gui.CrystalOverlay;
import net.h8sh.playermod.gui.ManaBarOverlay;
import net.h8sh.playermod.gui.ManaOverlay;
import net.h8sh.playermod.gui.NarratorOverlay;
import net.h8sh.playermod.capability.profession.Profession;
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
            int currentProfession = Profession.getProfession();

            switch (currentProfession) {
                case 1: //Paladin
                    if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.INTERACTION_KEY.consumeClick()) {

                    }
                case 2: //Wizard
                    if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.INTERACTION_KEY.consumeClick()) {

                    }
                case 3: //Druid
                    if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.INTERACTION_KEY.consumeClick()) {

                    }
            }
        }

    }

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvent {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.FIRST_SPELL_KEY);
            event.register(KeyBinding.SECOND_SPELL_KEY);
            event.register(KeyBinding.THIRD_SPELL_KEY);
            event.register(KeyBinding.ULTIMATE_SPELL_KEY);
            event.register(KeyBinding.INTERACTION_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("narrator", NarratorOverlay.HUD_NARRATOR);
            event.registerAboveAll("mana", ManaOverlay.HUD_MANA);
            event.registerAboveAll("crystal", CrystalOverlay.HUD_CRYSTAL);
            event.registerAboveAll("mana_bar", ManaBarOverlay.HUD_MANA_BAR);
        }

    }
}
