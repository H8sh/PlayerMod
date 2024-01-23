package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundSeenAdvancementsPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class SkillScreen extends Screen implements ClientSkills.Listener {
    public static final ResourceLocation TABS_LOCATION = new ResourceLocation("textures/gui/advancements/tabs.png");
    public static final int WINDOW_WIDTH = 252;
    public static final int WINDOW_HEIGHT = 140;
    public static final int WINDOW_INSIDE_WIDTH = 234;
    public static final int WINDOW_INSIDE_HEIGHT = 113;
    public static final int BACKGROUND_TILE_WIDTH = 16;
    public static final int BACKGROUND_TILE_HEIGHT = 16;
    public static final int BACKGROUND_TILE_COUNT_X = 14;
    public static final int BACKGROUND_TILE_COUNT_Y = 7;
    private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
    private static final int WINDOW_INSIDE_X = 9;
    private static final int WINDOW_INSIDE_Y = 18;
    private static final int WINDOW_TITLE_X = 8;
    private static final int WINDOW_TITLE_Y = 6;
    private static final Component VERY_SAD_LABEL = Component.translatable("advancements.sad_label");
    private static final Component NO_SKILLS_LABEL = Component.translatable("advancements.empty");
    private static final Component TITLE = Component.translatable("gui.advancements");
    private static int tabPage, maxPages;
    private final ClientSkills skills;
    private final Map<Skill, SkillTab> tabs = Maps.newLinkedHashMap();
    @Nullable
    private SkillTab selectedTab;
    private boolean isScrolling;

    public SkillScreen(ClientSkills clientSkills) {
        super(GameNarrator.NO_TITLE);
        this.skills = clientSkills;
    }

    protected void init() {
        this.tabs.clear();
        this.selectedTab = null;
        this.skills.setListener(this);
        if (this.selectedTab == null && !this.tabs.isEmpty()) {
            this.skills.setSelectedTab(this.tabs.values().iterator().next().getSkill(), true);
        } else {
            this.skills.setSelectedTab(this.selectedTab == null ? null : this.selectedTab.getSkill(), true);
        }
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

    public void removed() {
        this.skills.setListener((ClientSkills.Listener) null);
        ClientPacketListener clientpacketlistener = this.minecraft.getConnection();
        if (clientpacketlistener != null) {
            clientpacketlistener.send(ServerboundSeenAdvancementsPacket.closedScreen());
        }

    }

    /**
     * Called when a mouse button is clicked within the GUI element.
     * <p>
     *
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pButton the button that was clicked.
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0) {
            int i = (this.width - 252) / 2;
            int j = (this.height - 140) / 2;

            for (SkillTab skillTab : this.tabs.values()) {
                if (skillTab.getPage() == tabPage && skillTab.isMouseOver(i, j, pMouseX, pMouseY)) {
                    this.skills.setSelectedTab(skillTab.getSkill(), true);
                    break;
                }
            }
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    /**
     * Called when a keyboard key is pressed within the GUI element.
     * <p>
     *
     * @param pKeyCode   the key code of the pressed key.
     * @param pScanCode  the scan code of the pressed key.
     * @param pModifiers the keyboard modifiers.
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.minecraft.options.keyAdvancements.matches(pKeyCode, pScanCode)) {
            this.minecraft.setScreen((Screen) null);
            this.minecraft.mouseHandler.grabMouse();
            return true;
        } else {
            return super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
    }

    /**
     * Renders the graphical user interface (GUI) element.
     *
     * @param pGuiGraphics the GuiGraphics object used for rendering.
     * @param pMouseX      the x-coordinate of the mouse cursor.
     * @param pMouseY      the y-coordinate of the mouse cursor.
     * @param pPartialTick the partial tick time.
     */
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

    /**
     * Called when the mouse is dragged within the GUI element.
     * <p>
     *
     * @param pMouseX the X coordinate of the mouse.
     * @param pMouseY the Y coordinate of the mouse.
     * @param pButton the button that is being dragged.
     * @param pDragX  the X distance of the drag.
     * @param pDragY  the Y distance of the drag.
     * @return {@code true} if the event is consumed, {@code false} otherwise.
     */
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
            pGuiGraphics.drawCenteredString(this.font, NO_SKILLS_LABEL, i, pOffsetY + 18 + 56 - 9 / 2, -1);
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

            for (SkillTab skillTab1 : this.tabs.values()) {
                if (skillTab1.getPage() == tabPage)
                    skillTab1.drawIcon(pGuiGraphics, pOffsetX, pOffsetY);
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

    public void onAddAdvancementRoot(Skill skill) {
        SkillTab skillTab = SkillTab.create(this.minecraft, this, this.tabs.size(), skill);
        if (skillTab != null) {
            this.tabs.put(skill, skillTab);
        }
    }

    public void onRemoveAdvancementRoot(Skill skill) {
    }

    public void onAddAdvancementTask(Skill skill) {
        SkillTab skillTab = this.getTab(skill);
        if (skillTab != null) {
            skillTab.addAdvancement(skill);
        }

    }

    public void onRemoveAdvancementTask(Skill skill) {
    }

    public void onUpdateAdvancementProgress(Skill skill, SkillProgress pProgress) {
        SkillWidget skillWidget = this.getSkillWidget(skill);
        if (skillWidget != null) {
            skillWidget.setProgress(pProgress);
        }

    }

    public void onSelectedTabChanged(@Nullable Skill skill) {
        this.selectedTab = this.tabs.get(skill);
    }

    public void onAdvancementsCleared() {
        this.tabs.clear();
        this.selectedTab = null;
    }

    @Nullable
    public SkillWidget getSkillWidget(Skill skill) {
        SkillTab skillTab = this.getTab(skill);
        return skillTab == null ? null : skillTab.getWidget(skill);
    }

    @Nullable
    private SkillTab getTab(Skill skill) {
        while (skill.getParent() != null) {
            skill = skill.getParent();
        }

        return this.tabs.get(skill);
    }
}
