package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.gui.CrystalOverlay;
import net.h8sh.playermod.gui.ManaBarOverlay;
import net.h8sh.playermod.gui.ManaOverlay;
import net.h8sh.playermod.gui.NarratorOverlay;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseAquaC2SPacket;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseFireC2SPacket;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseSpiritusC2SPacket;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseWindC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.GatherManaC2SPacket;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToMansionHuntedC2SPacket;
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
            var currentProfession = Profession.getProfession();

            switch (currentProfession) {
                case PALADIN:
                    if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {
                        ModMessages.sendToServer(new OnChangedDimensionToMansionHuntedC2SPacket());
                    }
                    if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.INTERACTION_KEY.consumeClick()) {

                    }
                    if (KeyBinding.RIDING_KEY.consumeClick()) {

                    }
                case WIZARD:
                    if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {
                        ModMessages.sendToServer(new GatherManaC2SPacket());
                    }
                    if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {
                        // Done with mod events with tick method
                    }
                    if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {

                    }
                    if (KeyBinding.INTERACTION_KEY.consumeClick()) {

                    }
                    if (KeyBinding.RIDING_KEY.consumeClick()) {

                    }
                case DRUID:
                    //TODO: change sells between each metamorphoses
                    if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {
                        ModMessages.sendToServer(new MetamorphoseAquaC2SPacket());
                    }
                    if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {
                        ModMessages.sendToServer(new MetamorphoseFireC2SPacket());
                    }
                    if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {
                        ModMessages.sendToServer(new MetamorphoseWindC2SPacket());
                    }
                    if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {
                        ModMessages.sendToServer(new MetamorphoseSpiritusC2SPacket());
                    }
                    if (KeyBinding.INTERACTION_KEY.consumeClick()) {

                    }
                    if (KeyBinding.RIDING_KEY.consumeClick()) {

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
            event.register(KeyBinding.RIDING_KEY);
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
