package net.h8sh.playermod.skill;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SkillTab {
    private final Minecraft minecraft;
    private final SkillScreen screen;
    private final SkillTabType type;
    private final int index;
    private final Skill skill;
    private final DisplayInfo display;
    private final ItemStack icon;
    private final Component title;
    private final SkillWidget root;
    private final Map<Skill, SkillWidget> widgets = Maps.newLinkedHashMap();
    private double scrollX;
    private double scrollY;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    private float fade;
    private boolean centered;
    private int page;

    public SkillTab(Minecraft pMinecraft, SkillScreen pScreen, SkillTabType pType, int pIndex, Skill pAdvancement, DisplayInfo pDisplay) {
        this.minecraft = pMinecraft;
        this.screen = pScreen;
        this.type = pType;
        this.index = pIndex;
        this.skill = pAdvancement;
        this.display = pDisplay;
        this.icon = pDisplay.getIcon();
        this.title = pDisplay.getTitle();
        this.root = new SkillWidget(this, pMinecraft, pAdvancement, pDisplay);
        this.addWidget(this.root, pAdvancement);
    }

    public SkillTab(Minecraft mc, SkillScreen screen, SkillTabType type, int index, int page, Skill adv, DisplayInfo info) {
        this(mc, screen, type, index, adv, info);
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public SkillTabType getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public Skill getSkill() {
        return this.skill;
    }

    public Component getTitle() {
        return this.title;
    }

    public DisplayInfo getDisplay() {
        return this.display;
    }

    public void drawTab(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY, boolean pIsSelected) {
        this.type.draw(pGuiGraphics, pOffsetX, pOffsetY, pIsSelected, this.index);
    }

    public void drawIcon(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY) {
        this.type.drawIcon(pGuiGraphics, pOffsetX, pOffsetY, this.index, this.icon);
    }

    public void drawContents(GuiGraphics pGuiGraphics, int pX, int pY) {
        if (!this.centered) {
            this.scrollX = (double)(117 - (this.maxX + this.minX) / 2);
            this.scrollY = (double)(56 - (this.maxY + this.minY) / 2);
            this.centered = true;
        }

        pGuiGraphics.enableScissor(pX, pY, pX + 234, pY + 113);
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float)pX, (float)pY, 0.0F);
        ResourceLocation resourcelocation = Objects.requireNonNullElse(this.display.getBackground(), TextureManager.INTENTIONAL_MISSING_TEXTURE);
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);
        int k = i % 16;
        int l = j % 16;

        for(int i1 = -1; i1 <= 15; ++i1) {
            for(int j1 = -1; j1 <= 8; ++j1) {
                pGuiGraphics.blit(resourcelocation, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
            }
        }

        this.root.drawConnectivity(pGuiGraphics, i, j, true);
        this.root.drawConnectivity(pGuiGraphics, i, j, false);
        this.root.draw(pGuiGraphics, i, j);
        pGuiGraphics.pose().popPose();
        pGuiGraphics.disableScissor();
    }

    public void drawTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pWidth, int pHeight) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate(0.0F, 0.0F, -200.0F);
        pGuiGraphics.fill(0, 0, 234, 113, Mth.floor(this.fade * 255.0F) << 24);
        boolean flag = false;
        int i = Mth.floor(this.scrollX);
        int j = Mth.floor(this.scrollY);
        if (pMouseX > 0 && pMouseX < 234 && pMouseY > 0 && pMouseY < 113) {
            for(SkillWidget advancementwidget : this.widgets.values()) {
                if (advancementwidget.isMouseOver(i, j, pMouseX, pMouseY)) {
                    flag = true;
                    advancementwidget.drawHover(pGuiGraphics, i, j, this.fade, pWidth, pHeight);
                    break;
                }
            }
        }

        pGuiGraphics.pose().popPose();
        if (flag) {
            this.fade = Mth.clamp(this.fade + 0.02F, 0.0F, 0.3F);
        } else {
            this.fade = Mth.clamp(this.fade - 0.04F, 0.0F, 1.0F);
        }

    }

    public boolean isMouseOver(int pOffsetX, int pOffsetY, double pMouseX, double pMouseY) {
        return this.type.isMouseOver(pOffsetX, pOffsetY, this.index, pMouseX, pMouseY);
    }

    @Nullable
    public static SkillTab create(Minecraft pMinecraft, SkillScreen pScreen, int pTabIndex, Skill display) {
        if (display.getDisplay() == null) {
            return null;
        } else {
            for(SkillTabType skillTabType : SkillTabType.values()) {
                if ((pTabIndex % SkillTabType.MAX_TABS) < skillTabType.getMax()) {
                    return new SkillTab(pMinecraft, pScreen, skillTabType, pTabIndex % SkillTabType.MAX_TABS, pTabIndex / SkillTabType.MAX_TABS, display, display.getDisplay());
                }

                pTabIndex -= skillTabType.getMax();
            }

            return null;
        }
    }

    public void scroll(double pDragX, double pDragY) {
        if (this.maxX - this.minX > 234) {
            this.scrollX = Mth.clamp(this.scrollX + pDragX, (double)(-(this.maxX - 234)), 0.0D);
        }

        if (this.maxY - this.minY > 113) {
            this.scrollY = Mth.clamp(this.scrollY + pDragY, (double)(-(this.maxY - 113)), 0.0D);
        }

    }

    public void addAdvancement(Skill skill) {
        if (skill.getDisplay() != null) {
            SkillWidget advancementwidget = new SkillWidget(this, this.minecraft, skill, skill.getDisplay());
            this.addWidget(advancementwidget, skill);
        }
    }

    private void addWidget(SkillWidget pWidget, Skill skill) {
        this.widgets.put(skill, pWidget);
        int i = pWidget.getX();
        int j = i + 28;
        int k = pWidget.getY();
        int l = k + 27;
        this.minX = Math.min(this.minX, i);
        this.maxX = Math.max(this.maxX, j);
        this.minY = Math.min(this.minY, k);
        this.maxY = Math.max(this.maxY, l);

        for(SkillWidget skillWidget : this.widgets.values()) {
            skillWidget.attachToParent();
        }

    }

    @Nullable
    public SkillWidget getWidget(Skill skill) {
        return this.widgets.get(skill);
    }

    public SkillScreen getScreen() {
        return this.screen;
    }
}
