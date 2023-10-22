package net.h8sh.playermod.mixin;

import com.google.common.collect.Lists;
import net.h8sh.playermod.capability.profession.Profession;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.recipebook.OverlayRecipeComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(RecipeBookPage.class)
public class MixinRecipeBookPage {
    private static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");

    @Final
    @Shadow
    private OverlayRecipeComponent overlay = new OverlayRecipeComponent();

    @Shadow
    @Nullable
    private RecipeButton hoveredButton;

    @Shadow
    private int totalPages;

    @Shadow
    private int currentPage;
    @Shadow
    private Minecraft minecraft;

    @Final
    @Shadow
    private List<RecipeButton> buttons = Lists.newArrayListWithCapacity(20);

    @Shadow
    private RecipeBook recipeBook;

    @Shadow
    private StateSwitchingButton forwardButton;

    @Shadow
    private StateSwitchingButton backButton;

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    public void init(Minecraft pMinecraft, int pX, int pY, CallbackInfo ci) {
        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        if (currentProfession == 0) {
            ci.cancel();
            this.minecraft = pMinecraft;
            this.recipeBook = pMinecraft.player.getRecipeBook();

            for (int i = 0; i < this.buttons.size(); ++i) {
                this.buttons.get(i).setPosition(pX + 11 + 25 * (i % 5) - 18, pY + 31 + 25 * (i / 5));
            }

            this.forwardButton = new StateSwitchingButton(pX + 93 - 18, pY + 137, 12, 17, false);
            this.forwardButton.initTextureValues(1, 208, 13, 18, RECIPE_BOOK_LOCATION);
            this.backButton = new StateSwitchingButton(pX + 38 - 18, pY + 137, 12, 17, true);
            this.backButton.initTextureValues(1, 208, 13, 18, RECIPE_BOOK_LOCATION);
        }
    }


    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics pGuiGraphics, int p_281888_, int p_281904_, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        if (currentProfession == 0) {
            ci.cancel();
            if (this.totalPages > 1) {
                String s = this.currentPage + 1 + "/" + this.totalPages;
                int i = this.minecraft.font.width(s);
                pGuiGraphics.drawString(this.minecraft.font, s, p_281888_ - i / 2 + 73 - 18, p_281904_ + 141, -1, false);
            }

            this.hoveredButton = null;

            for (RecipeButton recipebutton : this.buttons) {
                recipebutton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                if (recipebutton.visible && recipebutton.isHoveredOrFocused()) {
                    this.hoveredButton = recipebutton;
                }
            }

            this.backButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.forwardButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
            this.overlay.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }
    }

}
