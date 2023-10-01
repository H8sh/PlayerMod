package net.h8sh.playermod.gui.wizard;

import com.mojang.blaze3d.systems.RenderSystem;
import net.h8sh.playermod.PlayerMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaBarOverlay {

    private static final ResourceLocation EMPTY_BAR = new ResourceLocation(PlayerMod.MODID,
            "textures/gui/mana_bar/empty_bar.png");
    private static final ResourceLocation MANA_FILLED = new ResourceLocation(PlayerMod.MODID,
            "textures/gui/mana_bar/mana_filled.png");

    public static final IGuiOverlay HUD_MANA_BAR = ((ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        int x = screenWidth / 2;
        int y = screenHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, EMPTY_BAR);
        for (int i = 0; i < 10; i++) {
            guiGraphics.blit(EMPTY_BAR, x - 100 + (i * 9), y - 56, 0, 0, 24, 13,
                    24, 13);
        }

        RenderSystem.setShaderTexture(0, MANA_FILLED);
        for (int i = 0; i < 10; i++) {
            if (35 > 10 * i) { //(ClientManaData.getPlayerMana() > 10 * i) {
                guiGraphics.blit(MANA_FILLED, x - 100 + (i * 9), y - 56, 0, 0, 24, 13,
                        24, 13);
            } else {
                break;
            }
        }
    });

}