package net.h8sh.playermod.gui.profession;

import net.h8sh.playermod.capability.narrator.ClientNarratorData;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class NarratorOverlay {

    public static final IGuiOverlay HUD_NARRATOR = ((ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        String toDisplay = ClientNarratorData.getNarrator() + ClientNarratorData.getMessage();
        guiGraphics.drawString(gui.getFont(), toDisplay, 10, 10, 10);

    });

}
