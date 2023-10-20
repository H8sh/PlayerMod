package net.h8sh.playermod.event;


import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.SpellManager;
import net.h8sh.playermod.animation.handler.AnimationHandler;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.profession.reader.ProfessionTypes;
import net.h8sh.playermod.gui.berserk.ChargeBarOverlay;
import net.h8sh.playermod.gui.berserk.RageBarOverlay;
import net.h8sh.playermod.gui.profession.ArrowOverlay;
import net.h8sh.playermod.gui.profession.NarratorOverlay;
import net.h8sh.playermod.gui.profession.ReputationOverlay;
import net.h8sh.playermod.gui.profession.SpellBarOverlay;
import net.h8sh.playermod.gui.wizard.AoEBarOverlay;
import net.h8sh.playermod.gui.wizard.CrystalOverlay;
import net.h8sh.playermod.gui.wizard.ManaBarOverlay;
import net.h8sh.playermod.gui.wizard.ManaOverlay;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.utils.DashC2SPacket;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents {
    private static boolean isHotBar = true;
    private static boolean isKeysShown = true;

    public static boolean getHotBar() {
        return isHotBar;
    }

    public static boolean getKeysShown() {
        return isKeysShown;
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

            //UTILS: ---------------------------------------------------------------------------------------------------

            if (!shouldRenderHotBar && minecraft.options.keySwapOffhand.consumeClick()) {
                /*
                 * This is required otherwise if a swap is executed on the spell bar menu, this can make the item on the hot bar disappeared
                 */
                minecraft.options.keySwapOffhand.setDown(false);
            }
            if (!shouldRenderHotBar && minecraft.options.keyInventory.consumeClick()) {
                /*
                 * We don't want to display the name of items and blocks if in spell bar, but this must be seen if inventory is open
                 */
                minecraft.options.keyInventory.setDown(false);
            }
            if (currentProfession == Profession.Professions.BASIC && KeyBinding.INVENTORY_SWITCH_KEY.consumeClick()) {
                /*
                 * As Basic, the player is not allowed to get any spell
                 */
                KeyBinding.INVENTORY_SWITCH_KEY.setDown(false);
            }
            if (KeyBinding.INVENTORY_SWITCH_KEY.consumeClick() && Profession.getProfession() != Profession.Professions.BASIC) {
                isHotBar = !isHotBar;
            }
            if (KeyBinding.SHOW_KEYS_KEY.consumeClick() && Profession.getProfession() != Profession.Professions.BASIC) {
                isKeysShown = !isKeysShown;
            }

            //SPELLS: --------------------------------------------------------------------------------------------------

            if (KeyBinding.FIRST_SPELL_KEY.consumeClick()) {
                String firstSpell = ProfessionTypes.getProfessionType().getTypes().get(currentProfession.getId()).getProfessionType().getSkills().get(0).getFirstSpell();
                SpellManager.activeSpell(firstSpell, minecraft);
            }
            if (KeyBinding.SECOND_SPELL_KEY.consumeClick()) {
                String secondSpell = ProfessionTypes.getProfessionType().getTypes().get(currentProfession.getId()).getProfessionType().getSkills().get(0).getSecondSpell();
                SpellManager.activeSpell(secondSpell, minecraft);
            }
            if (KeyBinding.THIRD_SPELL_KEY.consumeClick()) {
                String thirdSpell = ProfessionTypes.getProfessionType().getTypes().get(currentProfession.getId()).getProfessionType().getSkills().get(0).getThirdSpell();
                SpellManager.activeSpell(thirdSpell, minecraft);
            }
            if (KeyBinding.ULTIMATE_SPELL_KEY.consumeClick()) {
                String ultimateSpell = ProfessionTypes.getProfessionType().getTypes().get(currentProfession.getId()).getProfessionType().getSkills().get(0).getUltimate();
                SpellManager.activeSpell(ultimateSpell, minecraft);
            }

            if (KeyBinding.INTERACTION_KEY.consumeClick()) {
                minecraft.player.respawn();
            }
            if (KeyBinding.RIDING_KEY.consumeClick()) {
                //TODO
            }

            if (KeyBinding.DASH_KEY.isDown() && (Minecraft.getInstance().options.keyRight.isDown() || Minecraft.getInstance().options.keyLeft.isDown() || Minecraft.getInstance().options.keyUp.isDown() || Minecraft.getInstance().options.keyDown.isDown())) {
                ModMessages.sendToServer(new DashC2SPacket());
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
            event.register(KeyBinding.SHOW_KEYS_KEY);
            event.register(KeyBinding.DASH_KEY);
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAboveAll("narrator", NarratorOverlay.HUD_NARRATOR);
            event.registerAboveAll("mana", ManaOverlay.HUD_MANA);
            event.registerAboveAll("crystal", CrystalOverlay.HUD_CRYSTAL);
            event.registerAboveAll("mana_bar", ManaBarOverlay.HUD_MANA_BAR);
            event.registerAboveAll("reputation_bar", ReputationOverlay.HUD_REPUTATION);
            event.registerAboveAll("spell_bar", SpellBarOverlay.HUD_SPELL_BAR);
            event.registerAboveAll("arrow_bar", ArrowOverlay.HUD_ARROW);
            event.registerAboveAll("aoe_progress_bar", AoEBarOverlay.HUD_AOE_BAR_PROGRESS);
            event.registerAboveAll("charge_progress_bar", ChargeBarOverlay.HUD_CHARGE_BAR_PROGRESS);
            event.registerAboveAll("rage_progress_bar", RageBarOverlay.HUD_RAGE_BAR_PROGRESS);
        }

    }
}
