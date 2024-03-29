package net.h8sh.playermod.skill;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.data.advancements.packs.VanillaAdvancementProvider;
import net.minecraft.data.advancements.packs.VanillaAdventureAdvancements;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SkillWidget {
    private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
    private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
    private final SkillTab tab;
    private final Skill advancement;
    private final DisplayInfo display;
    private final FormattedCharSequence title;
    private final int width;
    private final List<FormattedCharSequence> description;
    private final Minecraft minecraft;
    private final List<SkillWidget> children = Lists.newArrayList();
    private final int x;
    private final int y;
    @Nullable
    private SkillWidget parent;

    public SkillWidget(SkillTab pTab, Minecraft pMinecraft, Skill skill, DisplayInfo pDisplay) {
        this.tab = pTab;
        this.advancement = skill;
        this.display = pDisplay;
        this.minecraft = pMinecraft;
        this.title = Language.getInstance().getVisualOrder(pMinecraft.font.substrByWidth(pDisplay.getTitle(), 163));
        this.x = Mth.floor(pDisplay.getX() * 28.0F);
        this.y = Mth.floor(pDisplay.getY() * 27.0F);
        int l = 29 + pMinecraft.font.width(this.title);
        this.description = Language.getInstance().getVisualOrder(this.findOptimalLines(ComponentUtils.mergeStyles(pDisplay.getDescription().copy(), Style.EMPTY.withColor(pDisplay.getFrame().getChatColor())), l));

        for (FormattedCharSequence formattedcharsequence : this.description) {
            l = Math.max(l, pMinecraft.font.width(formattedcharsequence));
        }

        this.width = l + 3 + 5;
    }

    private static float getMaxWidth(StringSplitter pManager, List<FormattedText> pText) {
        return (float) pText.stream().mapToDouble(pManager::stringWidth).max().orElse(0.0D);
    }

    private List<FormattedText> findOptimalLines(Component pComponent, int pMaxWidth) {
        StringSplitter stringsplitter = this.minecraft.font.getSplitter();
        List<FormattedText> list = null;
        float f = Float.MAX_VALUE;

        for (int i : TEST_SPLIT_OFFSETS) {
            List<FormattedText> list1 = stringsplitter.splitLines(pComponent, pMaxWidth - i, Style.EMPTY);
            float f1 = Math.abs(getMaxWidth(stringsplitter, list1) - (float) pMaxWidth);
            if (f1 <= 10.0F) {
                return list1;
            }

            if (f1 < f) {
                f = f1;
                list = list1;
            }
        }

        return list;
    }

    @Nullable
    private SkillWidget getFirstVisibleParent(Skill skill) {
        do {
            skill = skill.getParent();
        } while (skill != null && skill.getDisplay() == null);

        return skill != null && skill.getDisplay() != null ? this.tab.getWidget(skill) : null;
    }

    public void drawConnectivity(GuiGraphics pGuiGraphics, int pX, int pY, boolean pDropShadow) {
        if (this.parent != null) {
            int i = pX + this.parent.x + 13;
            int j = pX + this.parent.x + 26 + 4;
            int k = pY + this.parent.y + 13;
            int l = pX + this.x + 13;
            int i1 = pY + this.y + 13;
            int j1 = pDropShadow ? -16777216 : -1;
            if (pDropShadow) {
                pGuiGraphics.hLine(j, i, k - 1, j1);
                pGuiGraphics.hLine(j + 1, i, k, j1);
                pGuiGraphics.hLine(j, i, k + 1, j1);
                pGuiGraphics.hLine(l, j - 1, i1 - 1, j1);
                pGuiGraphics.hLine(l, j - 1, i1, j1);
                pGuiGraphics.hLine(l, j - 1, i1 + 1, j1);
                pGuiGraphics.vLine(j - 1, i1, k, j1);
                pGuiGraphics.vLine(j + 1, i1, k, j1);
            } else {
                pGuiGraphics.hLine(j, i, k, j1);
                pGuiGraphics.hLine(l, j, i1, j1);
                pGuiGraphics.vLine(j, i1, k, j1);
            }
        }

        for (SkillWidget advancementwidget : this.children) {
            advancementwidget.drawConnectivity(pGuiGraphics, pX, pY, pDropShadow);
        }

    }

    public void draw(GuiGraphics pGuiGraphics, int pX, int pY) {
        SkillWidgetType advancementwidgettype = SkillWidgetType.OBTAINED;
        pGuiGraphics.blit(WIDGETS_LOCATION, pX + this.x + 3, pY + this.y, this.display.getFrame().getTexture(), 128 + advancementwidgettype.getIndex() * 26, 26, 26);
        pGuiGraphics.renderFakeItem(this.display.getIcon(), pX + this.x + 8, pY + this.y + 5);


        for (SkillWidget advancementwidget : this.children) {
            advancementwidget.draw(pGuiGraphics, pX, pY);
        }

    }

    public int getWidth() {
        return this.width;
    }

    public void addChild(SkillWidget pAdvancementWidget) {
        this.children.add(pAdvancementWidget);
    }

    public void drawHover(GuiGraphics pGuiGraphics, int pX, int pY, float pFade, int pWidth, int pHeight) {
        boolean flag = pWidth + pX + this.x + this.width + 26 >= this.tab.getScreen().width;
        String s = "done";
        int i = this.minecraft.font.width(s);
        boolean flag1 = 113 - pY - this.y - 26 <= 6 + this.description.size() * 9;
        float f = 1.0F;
        int j = Mth.floor(f * (float) this.width);
        SkillWidgetType advancementwidgettype;
        SkillWidgetType advancementwidgettype1;
        SkillWidgetType advancementwidgettype2;
        if (f >= 1.0F) {
            j = this.width / 2;
            advancementwidgettype = SkillWidgetType.OBTAINED;
            advancementwidgettype1 = SkillWidgetType.OBTAINED;
            advancementwidgettype2 = SkillWidgetType.OBTAINED;
        } else if (j < 2) {
            j = this.width / 2;
            advancementwidgettype = SkillWidgetType.UNOBTAINED;
            advancementwidgettype1 = SkillWidgetType.UNOBTAINED;
            advancementwidgettype2 = SkillWidgetType.UNOBTAINED;
        } else if (j > this.width - 2) {
            j = this.width / 2;
            advancementwidgettype = SkillWidgetType.OBTAINED;
            advancementwidgettype1 = SkillWidgetType.OBTAINED;
            advancementwidgettype2 = SkillWidgetType.UNOBTAINED;
        } else {
            advancementwidgettype = SkillWidgetType.OBTAINED;
            advancementwidgettype1 = SkillWidgetType.UNOBTAINED;
            advancementwidgettype2 = SkillWidgetType.UNOBTAINED;
        }

        int k = this.width - j;
        RenderSystem.enableBlend();
        int l = pY + this.y;
        int i1;
        if (flag) {
            i1 = pX + this.x - this.width + 26 + 6;
        } else {
            i1 = pX + this.x;
        }

        int j1 = 32 + this.description.size() * 9;
        if (!this.description.isEmpty()) {
            if (flag1) {
                pGuiGraphics.blitNineSliced(WIDGETS_LOCATION, i1, l + 26 - j1, this.width, j1, 10, 200, 26, 0, 52);
            } else {
                pGuiGraphics.blitNineSliced(WIDGETS_LOCATION, i1, l, this.width, j1, 10, 200, 26, 0, 52);
            }
        }

        pGuiGraphics.blit(WIDGETS_LOCATION, i1, l, 0, advancementwidgettype.getIndex() * 26, j, 26);
        pGuiGraphics.blit(WIDGETS_LOCATION, i1 + j, l, 200 - k, advancementwidgettype1.getIndex() * 26, k, 26);
        pGuiGraphics.blit(WIDGETS_LOCATION, pX + this.x + 3, pY + this.y, this.display.getFrame().getTexture(), 128 + advancementwidgettype2.getIndex() * 26, 26, 26);
        if (flag) {
            pGuiGraphics.drawString(this.minecraft.font, this.title, i1 + 5, pY + this.y + 9, -1);
            if (s != null) {
                pGuiGraphics.drawString(this.minecraft.font, s, pX + this.x - i, pY + this.y + 9, -1);
            }
        } else {
            pGuiGraphics.drawString(this.minecraft.font, this.title, pX + this.x + 32, pY + this.y + 9, -1);
            if (s != null) {
                pGuiGraphics.drawString(this.minecraft.font, s, pX + this.x + this.width - i - 5, pY + this.y + 9, -1);
            }
        }

        if (flag1) {
            for (int k1 = 0; k1 < this.description.size(); ++k1) {
                pGuiGraphics.drawString(this.minecraft.font, this.description.get(k1), i1 + 5, l + 26 - j1 + 7 + k1 * 9, -5592406, false);
            }
        } else {
            for (int l1 = 0; l1 < this.description.size(); ++l1) {
                pGuiGraphics.drawString(this.minecraft.font, this.description.get(l1), i1 + 5, pY + this.y + 9 + 17 + l1 * 9, -5592406, false);
            }
        }

        pGuiGraphics.renderFakeItem(this.display.getIcon(), pX + this.x + 8, pY + this.y + 5);
    }

    public boolean isMouseOver(int pX, int pY, int pMouseX, int pMouseY) {

        int i = pX + this.x;
        int j = i + 26;
        int k = pY + this.y;
        int l = k + 26;
        return pMouseX >= i && pMouseX <= j && pMouseY >= k && pMouseY <= l;

    }

    public void attachToParent() {
        if (this.parent == null && this.advancement.getParent() != null) {
            this.parent = this.getFirstVisibleParent(this.advancement);
            if (this.parent != null) {
                this.parent.addChild(this);
            }
        }

    }

    public int getY() {
        return this.y;
    }

    public int getX() {
        return this.x;
    }
}