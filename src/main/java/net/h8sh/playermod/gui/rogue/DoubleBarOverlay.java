package net.h8sh.playermod.gui.rogue;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class DoubleBarOverlay {
    private static final boolean fadeIn = true;
    private static float currentProgress;
    private static final float currentProgressD = 200; // reduce thanks to skill points;
    private static long fadeOutStart = -1L;
    private static long fadeInStart = -1L;

    public static void setCurrentProgress(float currentProgress) {
        DoubleBarOverlay.currentProgress = currentProgress;
    }

    public static float getCurrentProgress() {
        return currentProgress;
    }

    public static float getCurrentProgressD() {
        return currentProgressD;
    }

    public static final IGuiOverlay HUD_DOUBLE_BAR_PROGRESS = (ForgeGui gui, GuiGraphics pGuiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        int i = pGuiGraphics.guiWidth();
        long k = Util.getMillis();
        DoubleBarOverlay.fadeInStart = k;
        int k1 = (int) ((double) pGuiGraphics.guiHeight() * 0.8325D);
        double d1 = Math.min((double) pGuiGraphics.guiWidth() * 0.75D, pGuiGraphics.guiHeight()) * 0.25D;
        double d0 = d1 * 4.0D;
        int j1 = (int) (d0 * 0.5D);
        float f = DoubleBarOverlay.fadeOutStart > -1L ? (float) (k - DoubleBarOverlay.fadeOutStart) / 1000.0F : -1.0F;
        float f1 = DoubleBarOverlay.fadeInStart > -1L ? (float) (k - DoubleBarOverlay.fadeInStart) / 500.0F : -1.0F;
        DoubleBarOverlay.currentProgress = DoubleBarOverlay.currentProgress++ == DoubleBarOverlay.currentProgressD ? DoubleBarOverlay.currentProgressD: DoubleBarOverlay.currentProgress++;
        if (f < 1.0F) {
            DoubleBarOverlay.drawProgressBar(pGuiGraphics, i / 2 - j1 + 32, k1 - 1, i / 2 + j1 - 32, k1 + 3, 1.0F - Mth.clamp(f, 0.0F, 1.0F));
        }
        if (DoubleBarOverlay.fadeOutStart == -1L && (!DoubleBarOverlay.fadeIn || f1 >= 2.0F)) {
            DoubleBarOverlay.fadeOutStart = Util.getMillis();
        }
    };

    private static void drawProgressBar(GuiGraphics pGuiGraphics, int pMinX, int pMinY, int pMaxX, int pMaxY, float pPartialTick) {
        int i = Mth.ceil((float) (pMaxX - pMinX) * DoubleBarOverlay.currentProgress/ DoubleBarOverlay.currentProgressD);
        int j = Math.round(pPartialTick * 255.0F);
        int k = FastColor.ARGB32.color(j, 0, 0, 0);
        int q = FastColor.ARGB32.color(j, 192, 192, 192);
        pGuiGraphics.fill(pMinX , pMinY , pMinX + i, pMaxY , q);
        pGuiGraphics.fill(pMinX + 1, pMinY, pMaxX - 1, pMinY + 1, k);
        pGuiGraphics.fill(pMinX + 1, pMaxY, pMaxX - 1, pMaxY - 1, k);
        pGuiGraphics.fill(pMinX, pMinY, pMinX + 1, pMaxY, k);
        pGuiGraphics.fill(pMaxX, pMinY, pMaxX - 1, pMaxY, k);
    }

}