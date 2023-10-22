package net.h8sh.playermod.mixin;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.profession.Profession;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.screens.inventory.InventoryScreen.renderEntityInInventoryFollowsMouse;

@Mixin(InventoryScreen.class)
public class MixinInventoryScreen {

    private static final ResourceLocation INVENTORY_LOCATION_SLOTS = new ResourceLocation(PlayerMod.MODID, "textures/gui/container/inventory.png");

    @Inject(method = "renderBg", at = @At("HEAD"), cancellable = true)
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY, CallbackInfo ci) {

        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        if (currentProfession == 0) {

            ci.cancel();
            InventoryScreen inventoryScreen = (InventoryScreen) (Object) this;
            int i = inventoryScreen.leftPos;
            int j = inventoryScreen.topPos;
            pGuiGraphics.blit(INVENTORY_LOCATION_SLOTS, i - 18, j, 0, 0, inventoryScreen.imageWidth + 18, inventoryScreen.imageHeight);
            renderEntityInInventoryFollowsMouse(pGuiGraphics, i + 51, j + 75, 30, (float) (i + 51) - inventoryScreen.xMouse, (float) (j + 75 - 50) - inventoryScreen.yMouse, inventoryScreen.minecraft.player);
        }
    }

}
