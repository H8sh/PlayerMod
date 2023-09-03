package net.h8sh.playermod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.h8sh.playermod.event.ClientEvents;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.packs.resources.ReloadInstance;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.glfw.GLFW;

public class AoEBarOverlay  {
    private static final boolean fadeIn = true;
    private static float currentProgress;
    private static float currentProgressD = 200; // reduce thanks to skill points;
    private static long fadeOutStart = -1L;
    private static long fadeInStart = -1L;

    public static void setCurrentProgress(float currentProgress) {
        AoEBarOverlay.currentProgress = currentProgress;
    }

    public static float getCurrentProgress() {
        return currentProgress;
    }

    public static float getCurrentProgressD() {
        return currentProgressD;
    }

    public static final IGuiOverlay HUD_AOE_BAR_PROGRESS = (ForgeGui gui, GuiGraphics pGuiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        int i = pGuiGraphics.guiWidth();
        long k = Util.getMillis();
        AoEBarOverlay.fadeInStart = k;
        int k1 = (int) ((double) pGuiGraphics.guiHeight() * 0.8325D);
        double d1 = Math.min((double) pGuiGraphics.guiWidth() * 0.75D, pGuiGraphics.guiHeight()) * 0.25D;
        double d0 = d1 * 4.0D;
        int j1 = (int) (d0 * 0.5D);
        float f = AoEBarOverlay.fadeOutStart > -1L ? (float) (k - AoEBarOverlay.fadeOutStart) / 1000.0F : -1.0F;
        float f1 = AoEBarOverlay.fadeInStart > -1L ? (float) (k - AoEBarOverlay.fadeInStart) / 500.0F : -1.0F;
        AoEBarOverlay.currentProgress = AoEBarOverlay.currentProgress++ == AoEBarOverlay.currentProgressD ? AoEBarOverlay.currentProgressD: AoEBarOverlay.currentProgress++;
        if (f < 1.0F) {
            AoEBarOverlay.drawProgressBar(pGuiGraphics, i / 2 - j1 + 32, k1 - 1, i / 2 + j1 - 32, k1 + 3, 1.0F - Mth.clamp(f, 0.0F, 1.0F));
        }
        if (AoEBarOverlay.fadeOutStart == -1L && (!AoEBarOverlay.fadeIn || f1 >= 2.0F)) {
            AoEBarOverlay.fadeOutStart = Util.getMillis();
        }
    };

    private static void drawProgressBar(GuiGraphics pGuiGraphics, int pMinX, int pMinY, int pMaxX, int pMaxY, float pPartialTick) {
        int i = Mth.ceil((float) (pMaxX - pMinX) * AoEBarOverlay.currentProgress/AoEBarOverlay.currentProgressD);
        int j = Math.round(pPartialTick * 255.0F);
        int k = FastColor.ARGB32.color(j, 0, 0, 0);
        int q = FastColor.ARGB32.color(j, 0, 20, 100);
        pGuiGraphics.fill(pMinX , pMinY , pMinX + i, pMaxY , q);
        pGuiGraphics.fill(pMinX + 1, pMinY, pMaxX - 1, pMinY + 1, k);
        pGuiGraphics.fill(pMinX + 1, pMaxY, pMaxX - 1, pMaxY - 1, k);
        pGuiGraphics.fill(pMinX, pMinY, pMinX + 1, pMaxY, k);
        pGuiGraphics.fill(pMaxX, pMinY, pMaxX - 1, pMaxY, k);
    }

}