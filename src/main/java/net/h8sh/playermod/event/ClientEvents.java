package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.gui.*;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseAquaC2SPacket;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseFireC2SPacket;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseSpiritusC2SPacket;
import net.h8sh.playermod.networking.classes.druid.metamorphose.MetamorphoseWindC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.manapacket.GatherManaC2SPacket;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToMansionHuntedC2SPacket;
import net.h8sh.playermod.screen.profession.SkillScreen;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    private static boolean isHotBar = true;

    public static boolean getHotBar() {
        return isHotBar;
    }

    public static void setIsHotBar(boolean isHotBar) {
        ClientEvents.isHotBar = isHotBar;
    }

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, value = Dist.CLIENT)
    public static class ClientForgeEvents {

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean shouldRenderHotBar = ClientEvents.getHotBar();
            var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();

            if (!shouldRenderHotBar && minecraft.options.keySwapOffhand.consumeClick()) {
                /**
                 * This is required otherwise if a swap is executed on the spell bar menu, this can make the item on the hot bar disappeared
                 */
                minecraft.options.keySwapOffhand.setDown(false);
            }

            if (!shouldRenderHotBar && minecraft.options.keyInventory.consumeClick()) {
                /**
                 * We don't want to display the name of items and blocks if in spell bar, but this can be seen if inventory is open
                 */
                minecraft.options.keyInventory.setDown(false);
            }

            if (currentProfession == Profession.Professions.BASIC && KeyBinding.INVENTORY_SWITCH_KEY.consumeClick()) {
                /**
                 * As Basic, the player is not allowed to get any spell
                 */
                KeyBinding.INVENTORY_SWITCH_KEY.setDown(false);
            }

            if (KeyBinding.INVENTORY_SWITCH_KEY.consumeClick() && Profession.getProfession() != Profession.Professions.BASIC) {
                isHotBar = !isHotBar;
            }

            switch (currentProfession) {
                case PALADIN:
                    if (KeyBinding.SKILL_SCREEN_KEY.consumeClick()) {
                        Minecraft.getInstance().setScreen(new SkillScreen(Component.literal("Paladin Skills")));
                    }
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
                    if (KeyBinding.SKILL_SCREEN_KEY.consumeClick()) {
                        Minecraft.getInstance().setScreen(new SkillScreen(Component.literal("Wizard Skills")));
                    }
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
                    if (KeyBinding.SKILL_SCREEN_KEY.consumeClick()) {
                        Minecraft.getInstance().setScreen(new SkillScreen(Component.literal("Druid Skills")));
                    }
                    //TODO: change spells between each metamorphoses
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
            event.register(KeyBinding.INVENTORY_SWITCH_KEY);
            event.register(KeyBinding.SKILL_SCREEN_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("narrator", NarratorOverlay.HUD_NARRATOR);
            event.registerAboveAll("mana", ManaOverlay.HUD_MANA);
            event.registerAboveAll("crystal", CrystalOverlay.HUD_CRYSTAL);
            event.registerAboveAll("mana_bar", ManaBarOverlay.HUD_MANA_BAR);
            event.registerAboveAll("reputation_bar", ReputationOverlay.HUD_REPUTATION);
            event.registerAboveAll("spell_bar", SpellBarOverlay.HUD_SPELL_BAR);
        }

    }
}
