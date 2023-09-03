package net.h8sh.playermod.gui;

import net.h8sh.playermod.config.WonderlandsModClientConfigs;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class ManaOverlay {

    public static final IGuiOverlay HUD_MANA = (ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        String toDisplay = "Mana: " + 0 + "/" + 0; //"Mana: " + ClientManaData.getPlayerMana() + "/" + ClientManaData.getChunkMana();
        int x = WonderlandsModClientConfigs.MANA_HUD_X.get();
        int y = WonderlandsModClientConfigs.MANA_HUD_Y.get();
        if (x >= 0 && y >= 0) {
            guiGraphics.drawString(gui.getFont(), toDisplay, x, y, WonderlandsModClientConfigs.MANA_HUD_COLOR.get());
        }
    };

}