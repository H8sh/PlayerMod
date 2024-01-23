package net.h8sh.playermod.skill;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum SkillWidgetType {
    OBTAINED(0),
    UNOBTAINED(1);

    private final int y;

    private SkillWidgetType(int pY) {
        this.y = pY;
    }

    public int getIndex() {
        return this.y;
    }
}