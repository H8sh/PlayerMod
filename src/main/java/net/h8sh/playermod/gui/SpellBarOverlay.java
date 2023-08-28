package net.h8sh.playermod.gui;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.event.ClientEvents;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.glfw.GLFW;

public class SpellBarOverlay {
    public static final IGuiOverlay HUD_SPELL_BAR = (ForgeGui gui, GuiGraphics pGuiGraphics, float pPartialTick, int screenWidth, int screenHeight) -> {

        Player player = gui.minecraft.player;
        ResourceLocation WIDGETS_LOCATION = null;
        ItemStack itemStack = null;
        var currentProfession = Profession.getProfession();
        if (currentProfession == Profession.Professions.PALADIN) {
            itemStack = new ItemStack(ModItems.PALADIN_BOOK.get());
            WIDGETS_LOCATION = new ResourceLocation(PlayerMod.MODID, "textures/gui/spell_bar/paladin.png");
        }
        if (currentProfession == Profession.Professions.DRUID) {
            itemStack = new ItemStack(ModItems.DRUID_BOOK.get());
            WIDGETS_LOCATION = new ResourceLocation(PlayerMod.MODID, "textures/gui/spell_bar/druid.png");
        }
        if (currentProfession == Profession.Professions.WIZARD) {
            itemStack = new ItemStack(ModItems.WIZARD_BOOK.get());
            WIDGETS_LOCATION = new ResourceLocation(PlayerMod.MODID, "textures/gui/spell_bar/wizard.png");
        }


        int l = 1;
        int i = screenWidth / 2;
        int i2 = screenHeight - 16 - 3;
        pGuiGraphics.pose().translate(0.0F, 0.0F, -90.0F);
        pGuiGraphics.blit(WIDGETS_LOCATION, i - 91, screenHeight - 22, 0, 0, 182, 22);
        pGuiGraphics.blit(WIDGETS_LOCATION, i - 91 - 29, screenHeight - 23, 24, 22, 29, 24); // left case

        if (ClientEvents.getKeysShown()) {

            pGuiGraphics.drawString(gui.getFont(), GLFW.glfwGetKeyName(KeyBinding.SKILL_SCREEN_KEY.getKey().getValue(), 0), i - 91 - 29, screenHeight - 23, 8421504);
            pGuiGraphics.drawString(gui.getFont(), GLFW.glfwGetKeyName(KeyBinding.FIRST_SPELL_KEY.getKey().getValue(), 0), i - 91, screenHeight - 23, 8421504);
            pGuiGraphics.drawString(gui.getFont(), GLFW.glfwGetKeyName(KeyBinding.SECOND_SPELL_KEY.getKey().getValue(), 0), i - 91 + 21, screenHeight - 23, 8421504);
            pGuiGraphics.drawString(gui.getFont(), GLFW.glfwGetKeyName(KeyBinding.THIRD_SPELL_KEY.getKey().getValue(), 0), i - 91 + 42, screenHeight - 23, 8421504);
            pGuiGraphics.drawString(gui.getFont(), GLFW.glfwGetKeyName(KeyBinding.ULTIMATE_SPELL_KEY.getKey().getValue(), 0), i - 91 + 63, screenHeight - 23, 8421504);
        }

        renderSlot(pGuiGraphics, i - 91 - 26, i2, pPartialTick, player, itemStack, l++, gui);

    };

    private static void renderSlot(GuiGraphics pGuiGraphics, int pX, int pY, float pPartialTick, Player pPlayer, ItemStack pStack, int pSeed, ForgeGui gui) {
        if (!pStack.isEmpty()) {
            float f = (float) pStack.getPopTime() - pPartialTick;
            if (f > 0.0F) {
                float f1 = 1.0F + f / 5.0F;
                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().translate((float) (pX + 8), (float) (pY + 12), 0.0F);
                pGuiGraphics.pose().scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                pGuiGraphics.pose().translate((float) (-(pX + 8)), (float) (-(pY + 12)), 0.0F);
            }

            pGuiGraphics.renderItem(pPlayer, pStack, pX, pY, pSeed);
            if (f > 0.0F) {
                pGuiGraphics.pose().popPose();
            }

            pGuiGraphics.renderItemDecorations(gui.minecraft.font, pStack, pX, pY);
        }
    }

}