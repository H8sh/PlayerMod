package net.h8sh.playermod.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.reputation.ClientReputationData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ReputationOverlay {
    protected static final ResourceLocation GUI_ICONS_LOCATION = new ResourceLocation(PlayerMod.MODID, "textures/gui/reputation_bar/reputation_bar.png");

    public static final IGuiOverlay HUD_REPUTATION = (ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) -> {

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();

        if (ClientReputationData.getReputationNeeded() > 0) {
            int k = (int) (ClientReputationData.getReputationProgress() * 183.0F);
            int l = screenHeight - 32 + 3;
            guiGraphics.blit(GUI_ICONS_LOCATION, screenWidth / 2 - 91, l, 0, 64, 182, 5);
            if (k > 0) {
                guiGraphics.blit(GUI_ICONS_LOCATION, screenWidth / 2 - 91, l, 0, 69, k, 5);
            }
        }


        String s = "" + ClientReputationData.getReputation();
        int i1 = (screenWidth - gui.getFont().width(s)) / 2;
        int j1 = screenHeight - 31 - 4;
        guiGraphics.drawString(gui.getFont(), s, i1 + 1, j1, 0, false);
        guiGraphics.drawString(gui.getFont(), s, i1 - 1, j1, 0, false);
        guiGraphics.drawString(gui.getFont(), s, i1, j1 + 1, 0, false);
        guiGraphics.drawString(gui.getFont(), s, i1, j1 - 1, 0, false);
        guiGraphics.drawString(gui.getFont(), s, i1, j1, 16762137, false);


        RenderSystem.enableBlend();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);


    };

}