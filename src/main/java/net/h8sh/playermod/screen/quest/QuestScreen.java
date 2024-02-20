package net.h8sh.playermod.screen.quest;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class QuestScreen extends Screen {
    private static Component QUEST = Component.literal("Quest");

    protected QuestScreen() {
        super(QUEST);
    }

    protected void init() {
        super.init();
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        //TODO

    }

    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {

    }

    public boolean shouldCloseOnEsc() {
        return false;
    }

    public void onClose() {
        super.onClose();
    }
}
