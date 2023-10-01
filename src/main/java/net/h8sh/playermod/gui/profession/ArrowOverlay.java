package net.h8sh.playermod.gui.profession;

import com.mojang.blaze3d.systems.RenderSystem;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.event.ClientEvents;
import net.h8sh.playermod.util.KeyBinding;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.lwjgl.glfw.GLFW;

public class ArrowOverlay {

    private final static ResourceLocation ARROW = new ResourceLocation(PlayerMod.MODID, "textures/gui/arrow/arrow.png");

    public static final IGuiOverlay HUD_ARROW = (ForgeGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) -> {
        int i = screenWidth / 2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, ARROW);
        guiGraphics.blit(ARROW, i + 93, screenHeight - 18, 0.0F, 0.0F, 10, 16, 10, 16);

        if (ClientEvents.getKeysShown()) {
            String toString = KeyBinding.INVENTORY_SWITCH_KEY.getKey().getValue() == GLFW.GLFW_KEY_TAB ? "tab" : GLFW.glfwGetKeyName(KeyBinding.INVENTORY_SWITCH_KEY.getKey().getValue(), 0);
            guiGraphics.drawString(gui.getFont(), toString
                    , i + 105, screenHeight - 15, 8421504);
        }
    };

}