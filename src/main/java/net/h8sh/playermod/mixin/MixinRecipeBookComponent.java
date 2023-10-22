package net.h8sh.playermod.mixin;

import net.h8sh.playermod.capability.profession.Profession;
import net.minecraft.client.ClientRecipeBook;
import net.minecraft.client.Minecraft;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeBookPage;
import net.minecraft.client.gui.screens.recipebook.RecipeBookTabButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.RecipeBookMenu;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(RecipeBookComponent.class)
public abstract class MixinRecipeBookComponent {

    private static final ResourceLocation RECIPE_BOOK_LOCATION = new ResourceLocation("textures/gui/recipe_book.png");
    @Shadow
    protected StateSwitchingButton filterButton;
    @Shadow
    protected Minecraft minecraft;
    @Shadow
    protected RecipeBookMenu<?> menu;
    @Final
    @Shadow
    private List<RecipeBookTabButton> tabButtons;
    @Shadow
    private int xOffset;
    @Shadow
    private int width;
    @Shadow
    @Nullable
    private EditBox searchBox;
    @Shadow
    private int height;
    @Final
    @Shadow
    private RecipeBookPage recipeBookPage;
    @Shadow
    private ClientRecipeBook book;

    @Inject(method = "updateTabs", at = @At("HEAD"), cancellable = true)
    private void updateTabs(CallbackInfo ci) {
        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        if (currentProfession == 0) {
            ci.cancel();
            int i = (this.width - 147) / 2 - this.xOffset - 30;
            int j = (this.height - 166) / 2 + 3;
            int k = 27;
            int l = 0;

            for (RecipeBookTabButton recipebooktabbutton : this.tabButtons) {
                RecipeBookCategories recipebookcategories = recipebooktabbutton.getCategory();
                if (recipebookcategories != RecipeBookCategories.CRAFTING_SEARCH && recipebookcategories != RecipeBookCategories.FURNACE_SEARCH) {
                    if (recipebooktabbutton.updateVisibility(this.book)) {
                        recipebooktabbutton.setPosition(i - 18, j + 27 * l++);
                        recipebooktabbutton.startAnimation(this.minecraft);
                    }
                } else {
                    recipebooktabbutton.visible = true;
                    recipebooktabbutton.setPosition(i - 18, j + 27 * l++);
                }
            }
        }

    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)

    private void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick, CallbackInfo ci) {
        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        RecipeBookComponent recipeBookComponent = (RecipeBookComponent) (Object) this;

        if (currentProfession == 0) {
            ci.cancel();
            if (recipeBookComponent.isVisible()) {
                pGuiGraphics.pose().pushPose();
                pGuiGraphics.pose().translate(0.0F, 0.0F, 100.0F);
                int i = (this.width - 147) / 2 - this.xOffset;
                int j = (this.height - 166) / 2;
                pGuiGraphics.blit(RECIPE_BOOK_LOCATION, i - 18, j, 1, 1, 147, 166);
                this.searchBox.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                for (RecipeBookTabButton recipebooktabbutton : this.tabButtons) {
                    recipebooktabbutton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                }

                this.filterButton.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
                this.recipeBookPage.render(pGuiGraphics, i, j, pMouseX, pMouseY, pPartialTick);
                pGuiGraphics.pose().popPose();
            }
        }

    }

    @Redirect(method = "initVisuals", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;searchBox:Lnet/minecraft/client/gui/components/EditBox;", opcode = Opcodes.PUTFIELD))
    private void setEditBox(RecipeBookComponent instance, EditBox value) {
        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        int i = (this.width - 147) / 2 - this.xOffset;
        int j = (this.height - 166) / 2;
        if (currentProfession == 0) {
            this.searchBox = new EditBox(this.minecraft.font, i + 26 - 18, j + 14, 79, 9 + 3, Component.translatable("itemGroup.search"));
        }
        if (currentProfession != 0) {
            this.searchBox = new EditBox(this.minecraft.font, i + 26, j + 14, 79, 9 + 3, Component.translatable("itemGroup.search"));
        }
    }


    @Redirect(method = "initVisuals", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/recipebook/RecipeBookComponent;filterButton:Lnet/minecraft/client/gui/components/StateSwitchingButton;", opcode = Opcodes.PUTFIELD))
    private void setFilterButton(RecipeBookComponent instance, StateSwitchingButton value) {
        int currentProfession = (Profession.getProfession() == null || Profession.getProfession() == Profession.Professions.BASIC) ? 1 : 0;

        int i = (this.width - 147) / 2 - this.xOffset;
        int j = (this.height - 166) / 2;

        if (currentProfession == 0) {
            this.filterButton = new StateSwitchingButton(i + 110 - 18, j + 12, 26, 16, this.book.isFiltering(this.menu));
            this.updateFilterButtonTooltip();
        }
        if (currentProfession != 0) {
            this.filterButton = new StateSwitchingButton(i + 110, j + 12, 26, 16, this.book.isFiltering(this.menu));
            this.updateFilterButtonTooltip();
        }
    }

    @Shadow
    protected abstract void updateFilterButtonTooltip();

}
