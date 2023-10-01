package net.h8sh.playermod.screen.pnj;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.PnjBlockEntity;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.PnjEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;

public class PnjBlockScreen extends Screen {
    private static final Component TITLE = Component.literal("gui." + PlayerMod.MODID + ".pnj_block_screen");


    private Button button;

    public PnjBlockScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        super.init();

        if (this.minecraft == null) return;
        Level level = this.minecraft.level;
        if (level == null) return;

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);

        InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.width / 3 - 5, this.height / 3 + 110, 20, (float) 51 - pMouseX, (float) 25 - pMouseY, ModEntities.CUSTOM_PNJ.get().create(this.minecraft.level));

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        if (this.minecraft.level != null) {
            pGuiGraphics.fillGradient(this.width / 3 - 30, this.height / 3 + 60, this.width / 3 + 190, this.height / 3 + 120, -1072689136, -804253680);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.ScreenEvent.BackgroundRendered(this, pGuiGraphics));
        } else {
            this.renderDirtBackground(pGuiGraphics);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        PnjBlockEntity.closeScreen();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
