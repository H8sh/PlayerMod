package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen {
    public static final ResourceLocation TABS_LOCATION = new ResourceLocation("textures/gui/advancements/tabs.png");
    public static final int WINDOW_WIDTH = 252;
    private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
    private static final Component VERY_SAD_LABEL = Component.translatable("advancements.sad_label");
    private static final Component NO_ADVANCEMENTS_LABEL = Component.translatable("advancements.empty");
    private static final Component TITLE = Component.translatable("gui.advancements");
    private static int tabPage, maxPages;
    private final Map<Skill, SkillTab> tabs = Maps.newLinkedHashMap();
    @Nullable
    private SkillTab selectedTab;
    private boolean isScrolling;

    public SkillScreen() {
        super(GameNarrator.NO_TITLE);
    }

    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;

        if (this.tabs.size() > SkillTabType.MAX_TABS) {
            int guiLeft = (this.width - 252) / 2;
            int guiTop = (this.height - 140) / 2;
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal("<"), b -> tabPage = Math.max(tabPage - 1, 0))
                    .pos(guiLeft, guiTop - 50).size(20, 20).build());
            addRenderableWidget(net.minecraft.client.gui.components.Button.builder(Component.literal(">"), b -> tabPage = Math.min(tabPage + 1, maxPages))
                    .pos(guiLeft + WINDOW_WIDTH - 20, guiTop - 50).size(20, 20).build());
            maxPages = this.tabs.size() / SkillTabType.MAX_TABS;
        }
    }


    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;
        this.renderBackground(pGuiGraphics);
        if (maxPages != 0) {
            net.minecraft.network.chat.Component page = Component.literal(String.format("%d / %d", tabPage + 1, maxPages + 1));
            int width = this.font.width(page);
            pGuiGraphics.drawString(this.font, page.getVisualOrderText(), i + (252 / 2) - (width / 2), j - 44, -1);
        }
        this.renderInside(pGuiGraphics, pMouseX, pMouseY, i, j);
        this.renderWindow(pGuiGraphics, i, j);
        this.renderTooltips(pGuiGraphics, pMouseX, pMouseY, i, j);
    }

    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (pButton != 0) {
            this.isScrolling = false;
            return false;
        } else {
            if (!this.isScrolling) {
                this.isScrolling = true;
            } else if (this.selectedTab != null) {
                this.selectedTab.scroll(pDragX, pDragY);
            }

            return true;
        }
    }

    private void renderInside(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
        SkillTab skillTab = this.selectedTab;
        if (skillTab == null) {
            pGuiGraphics.fill(pOffsetX + 9, pOffsetY + 18, pOffsetX + 9 + 234, pOffsetY + 18 + 113, -16777216);
            int i = pOffsetX + 9 + 117;
            pGuiGraphics.drawCenteredString(this.font, NO_ADVANCEMENTS_LABEL, i, pOffsetY + 18 + 56 - 9 / 2, -1);
            pGuiGraphics.drawCenteredString(this.font, VERY_SAD_LABEL, i, pOffsetY + 18 + 113 - 9, -1);
        } else {
            skillTab.drawContents(pGuiGraphics, pOffsetX + 9, pOffsetY + 18);
        }
    }

    public void renderWindow(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY) {
        RenderSystem.enableBlend();
        pGuiGraphics.blit(WINDOW_LOCATION, pOffsetX, pOffsetY, 0, 0, 252, 140);
        if (this.tabs.size() > 1) {
            for (SkillTab skillTab : this.tabs.values()) {
                if (skillTab.getPage() == tabPage)
                    skillTab.drawTab(pGuiGraphics, pOffsetX, pOffsetY, skillTab == this.selectedTab);
            }

            for (SkillTab skillTab : this.tabs.values()) {
                if (skillTab.getPage() == tabPage)
                    skillTab.drawIcon(pGuiGraphics, pOffsetX, pOffsetY);
            }
        }

        pGuiGraphics.drawString(this.font, TITLE, pOffsetX + 8, pOffsetY + 6, 4210752, false);
    }

    private void renderTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
        if (this.selectedTab != null) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float) (pOffsetX + 9), (float) (pOffsetY + 18), 400.0F);
            RenderSystem.enableDepthTest();
            this.selectedTab.drawTooltips(pGuiGraphics, pMouseX - pOffsetX - 9, pMouseY - pOffsetY - 18, pOffsetX, pOffsetY);
            RenderSystem.disableDepthTest();
            pGuiGraphics.pose().popPose();
        }

        if (this.tabs.size() > 1) {
            for (SkillTab skillTab : this.tabs.values()) {
                if (skillTab.getPage() == tabPage && skillTab.isMouseOver(pOffsetX, pOffsetY, (double) pMouseX, (double) pMouseY)) {
                    pGuiGraphics.renderTooltip(this.font, skillTab.getTitle(), pMouseX, pMouseY);
                }
            }
        }

    }

}
