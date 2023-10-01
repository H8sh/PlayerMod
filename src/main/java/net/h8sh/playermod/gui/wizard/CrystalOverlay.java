package net.h8sh.playermod.gui.wizard;

import com.mojang.blaze3d.systems.RenderSystem;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.config.WonderlandsModClientConfigs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class CrystalOverlay {

    private final static ResourceLocation CRYSTAL = new ResourceLocation(PlayerMod.MODID, "textures/gui/crystal/crystal.png");

    public static final IGuiOverlay HUD_CRYSTAL = (ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        String toDisplay = "" + 0; //ClientCrystalData.getPlayerCrystal();
        int x = screenWidth - WonderlandsModClientConfigs.CRYSTAL_HUD_X.get() - 50;
        int y = WonderlandsModClientConfigs.CRYSTAL_HUD_Y.get();
        if (x >= 0 && y >= 0) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, CRYSTAL);
            guiGraphics.blit(CRYSTAL, x, y, 0, 0, 32, 32, 32, 32);
            guiGraphics.drawString(gui.getFont(), toDisplay, x + 35, y + 10, WonderlandsModClientConfigs.CRYSTAL_HUD_COLOR.get());
        }
    };

}