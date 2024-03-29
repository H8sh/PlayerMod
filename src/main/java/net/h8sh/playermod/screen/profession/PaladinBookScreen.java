package net.h8sh.playermod.screen.profession;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.entity.PaladinLecternEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;

@OnlyIn(Dist.CLIENT)
public class PaladinBookScreen extends Screen {
    public static final BookAccess EMPTY_ACCESS = new BookAccess() {
        /**
         * Returns the size of the book
         */
        public int getPageCount() {
            return 5;
        }

        public FormattedText getPageRaw(int p_98306_) {
            return FormattedText.EMPTY;
        }
    };
    public static final ResourceLocation BOOK_LOCATION = new ResourceLocation(PlayerMod.MODID, "textures/screen/paladinscreen.png");
    public static final ResourceLocation WOOD_FRAME_LOCATION = new ResourceLocation(PlayerMod.MODID, "textures/screen/wood_frame.png");
    protected static final int IMAGE_WIDTH = 250;
    protected static final int IMAGE_HEIGHT = 250;
    /**
     * Determines if a sound is played when the page is turned
     */
    private final boolean playTurnSound;
    private BookAccess bookAccess;
    private int currentPage;
    /**
     * Holds a copy of the page text, split into page width lines
     */
    private List<FormattedCharSequence> cachedPageComponents = Collections.emptyList();
    private int cachedPage = -1;
    private Component pageMsg = CommonComponents.EMPTY;
    private PageButton forwardButton;
    private PageButton backButton;

    public PaladinBookScreen(BookAccess pBookAccess) {
        this(pBookAccess, true);
    }

    public PaladinBookScreen() {
        this(EMPTY_ACCESS, true);
    }

    private PaladinBookScreen(BookAccess pBookAccess, boolean pPlayTurnSound) {
        super(GameNarrator.NO_TITLE);
        this.bookAccess = pBookAccess;
        this.playTurnSound = pPlayTurnSound;
    }

    static List<String> loadPages(CompoundTag pTag) {
        ImmutableList.Builder<String> builder = ImmutableList.builder();
        loadPages(pTag, builder::add);
        return builder.build();
    }

    public static void loadPages(CompoundTag pTag, Consumer<String> pConsumer) {
        ListTag listtag = pTag.getList("pages", 8).copy();
        IntFunction<String> intfunction;
        if (Minecraft.getInstance().isTextFilteringEnabled() && pTag.contains("filtered_pages", 10)) {
            CompoundTag compoundtag = pTag.getCompound("filtered_pages");
            intfunction = (p_169702_) -> {
                String s = String.valueOf(p_169702_);
                return compoundtag.contains(s) ? compoundtag.getString(s) : listtag.getString(p_169702_);
            };
        } else {
            intfunction = listtag::getString;
        }

        for (int i = 0; i < listtag.size(); ++i) {
            pConsumer.accept(intfunction.apply(i));
        }

    }

    public void setBookAccess(BookAccess pBookAccess) {
        this.bookAccess = pBookAccess;
        this.currentPage = Mth.clamp(this.currentPage, 0, pBookAccess.getPageCount());
        this.updateButtonVisibility();
        this.cachedPage = -1;
    }

    /**
     * Moves the book to the specified page and returns true if it exists, {@code false} otherwise.
     */
    public boolean setPage(int pPageNum) {
        int i = Mth.clamp(pPageNum, 0, this.bookAccess.getPageCount() - 1);
        if (i != this.currentPage) {
            this.currentPage = i;
            this.updateButtonVisibility();
            this.cachedPage = -1;
            return true;
        } else {
            return false;
        }
    }

    /**
     * I'm not sure why this exists. The function it calls is public and does all the work.
     */
    protected boolean forcePage(int pPageNum) {
        return this.setPage(pPageNum);
    }

    protected void init() {
        this.createMenuControls();
        this.createPageControlButtons();
    }

    protected void createMenuControls() {
        this.addRenderableWidget(Button.builder(Component.literal("Close"), (p_289629_) -> {
            this.onClose();
        }).bounds(this.width - 75, 225, 60, 20).build());
    }

    protected void createPageControlButtons() {
        int i = (this.width - 192) / 2;
        int j = 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(i + 215, 207, true, (p_98297_) -> {
            this.pageForward();
        }, this.playTurnSound));
        this.backButton = this.addRenderableWidget(new PageButton(i, 207, false, (p_98287_) -> {
            this.pageBack();
        }, this.playTurnSound));
        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return this.bookAccess.getPageCount();
    }

    /**
     * Moves the display back one page
     */
    protected void pageBack() {
        if (this.currentPage > 0) {
            --this.currentPage;
        }

        this.updateButtonVisibility();
    }

    /**
     * Moves the display forward one page
     */
    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            ++this.currentPage;
        }

        this.updateButtonVisibility();
    }

    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        } else {
            switch (pKeyCode) {
                case 266:
                    this.backButton.onPress();
                    return true;
                case 267:
                    this.forwardButton.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        /*
         * INIT RENDERING ----------------------------------------------------------------------------------------------
         */
        this.renderBackground(pGuiGraphics);

        int i = (this.width - 192) / 2;
        pGuiGraphics.blit(BOOK_LOCATION, i, -10, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        if (this.cachedPage != this.currentPage) {
            FormattedText formattedtext = this.bookAccess.getPage(this.currentPage);
            this.cachedPageComponents = this.font.split(formattedtext, 114);
            this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, Math.max(this.getNumPages(), 1)).withStyle(ChatFormatting.DARK_GRAY);
        }

        this.cachedPage = this.currentPage;
        int i1 = this.font.width(this.pageMsg);
        pGuiGraphics.drawString(this.font, this.pageMsg, i - i1 + 95, 207, 0, false);
        int k = Math.min(128 / 9, this.cachedPageComponents.size());

        for (int l = 0; l < k; ++l) {
            FormattedCharSequence formattedcharsequence = this.cachedPageComponents.get(l);
            pGuiGraphics.drawString(this.font, formattedcharsequence, i + 36, 32 + l * 9, 0, false);
        }

        Style style = this.getClickedComponentStyleAt((double) pMouseX, (double) pMouseY);
        if (style != null) {
            pGuiGraphics.renderComponentHoverEffect(this.font, style, pMouseX, pMouseY);
        }

        /*
         * MAIN BOOK ---------------------------------------------------------------------------------------------------
         */
        switch (this.currentPage) {
            case 0:
                InventoryScreen.renderEntityInInventoryFollowsMouse(pGuiGraphics, this.width / 4 + 92, this.height / 4 + 25, 20, (float) 51 - pMouseX, (float) 25 - pMouseY, this.minecraft.player);
                /*pGuiGraphics.blit(WOOD_FRAME_LOCATION, this.width / 4+45, this.height / 4 - 45, 0,0,128, 128, 128, 128);*/


            case 1:
                //TODO
            case 2:
                //TODO
            case 3:
                //TODO
            case 4:
                //TODO
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {
            Style style = this.getClickedComponentStyleAt(pMouseX, pMouseY);
            if (style != null && this.handleComponentClicked(style)) {
                return true;
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public boolean handleComponentClicked(Style pStyle) {
        ClickEvent clickevent = pStyle.getClickEvent();
        if (clickevent == null) {
            return false;
        } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String s = clickevent.getValue();

            try {
                int i = Integer.parseInt(s) - 1;
                return this.forcePage(i);
            } catch (Exception exception) {
                return false;
            }
        } else {
            boolean flag = super.handleComponentClicked(pStyle);
            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.closeScreen();
            }

            return flag;
        }
    }

    protected void closeScreen() {
        this.minecraft.setScreen((Screen) null);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        PaladinLecternEntity.closeScreen();
    }

    @Nullable
    public Style getClickedComponentStyleAt(double pMouseX, double pMouseY) {
        if (this.cachedPageComponents.isEmpty()) {
            return null;
        } else {
            int i = Mth.floor(pMouseX - (double) ((this.width - 192) / 2) - 36.0D);
            int j = Mth.floor(pMouseY - 2.0D - 30.0D);
            if (i >= 0 && j >= 0) {
                int k = Math.min(128 / 9, this.cachedPageComponents.size());
                if (i <= 114 && j < 9 * k + k) {
                    int l = j / 9;
                    if (l >= 0 && l < this.cachedPageComponents.size()) {
                        FormattedCharSequence formattedcharsequence = this.cachedPageComponents.get(l);
                        return this.minecraft.font.getSplitter().componentStyleAtWidth(formattedcharsequence, i);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public interface BookAccess {
        static BookAccess fromItem(ItemStack pStack) {
            if (pStack.is(Items.WRITTEN_BOOK)) {
                return new WrittenBookAccess(pStack);
            } else {
                return (BookAccess) (pStack.is(Items.WRITABLE_BOOK) ? new WritableBookAccess(pStack) : EMPTY_ACCESS);
            }
        }

        /**
         * Returns the size of the book
         */
        int getPageCount();

        FormattedText getPageRaw(int pIndex);

        default FormattedText getPage(int pPage) {
            return pPage >= 0 && pPage < this.getPageCount() ? this.getPageRaw(pPage) : FormattedText.EMPTY;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class WritableBookAccess implements BookAccess {
        private final List<String> pages;

        public WritableBookAccess(ItemStack pWrittenBookStack) {
            this.pages = readPages(pWrittenBookStack);
        }

        private static List<String> readPages(ItemStack pWrittenBookStack) {
            CompoundTag compoundtag = pWrittenBookStack.getTag();
            return (List<String>) (compoundtag != null ? loadPages(compoundtag) : ImmutableList.of());
        }

        /**
         * Returns the size of the book
         */
        public int getPageCount() {
            return this.pages.size();
        }

        public FormattedText getPageRaw(int pIndex) {
            return FormattedText.of(this.pages.get(pIndex));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class WrittenBookAccess implements BookAccess {
        private final List<String> pages;

        public WrittenBookAccess(ItemStack pWrittenBookStack) {
            this.pages = readPages(pWrittenBookStack);
        }

        private static List<String> readPages(ItemStack pWrittenBookStack) {
            CompoundTag compoundtag = pWrittenBookStack.getTag();
            return (List<String>) (compoundtag != null && WrittenBookItem.makeSureTagIsValid(compoundtag) ? loadPages(compoundtag) : ImmutableList.of(Component.Serializer.toJson(Component.translatable("book.invalid.tag").withStyle(ChatFormatting.DARK_RED))));
        }

        /**
         * Returns the size of the book
         */
        public int getPageCount() {
            return this.pages.size();
        }

        public FormattedText getPageRaw(int pIndex) {
            String s = this.pages.get(pIndex);

            try {
                FormattedText formattedtext = Component.Serializer.fromJson(s);
                if (formattedtext != null) {
                    return formattedtext;
                }
            } catch (Exception exception) {
            }

            return FormattedText.of(s);
        }
    }
}