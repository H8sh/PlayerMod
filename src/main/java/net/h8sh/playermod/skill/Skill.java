package net.h8sh.playermod.skill;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class Skill {

    @Nullable
    private final Skill parent;
    @Nullable
    private final DisplayInfo display;
    private final ResourceLocation id;
    private final Set<Skill> children = Sets.newLinkedHashSet();
    private final Component chatComponent;

    public Skill(ResourceLocation pId, @Nullable Skill pParent, @Nullable DisplayInfo pDisplay) {
        this.id = pId;
        this.display = pDisplay;
        this.parent = pParent;
        if (pParent != null) {
            pParent.addChild(this);
        }
        Component component = pDisplay.getTitle();
        ChatFormatting chatformatting = pDisplay.getFrame().getChatColor();
        Component component1 = ComponentUtils.mergeStyles(component.copy(), Style.EMPTY.withColor(chatformatting)).append("\n").append(pDisplay.getDescription());
        Component component2 = component.copy().withStyle((p_138316_) -> {
            return p_138316_.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, component1));
        });
        this.chatComponent = ComponentUtils.wrapInSquareBrackets(component2).withStyle(chatformatting);

    }

    public static Skill getRoot(Skill skill2) {
        Skill skill = skill2;

        while (true) {
            Skill skill1 = skill.getParent();
            if (skill1 == null) {
                return skill;
            }

            skill = skill1;
        }
    }


    public Component getChatComponent() {
        return this.chatComponent;
    }

    @Nullable
    public Skill getParent() {
        return this.parent;
    }

    public Skill getRoot() {
        return getRoot(this);
    }

    public Iterable<Skill> getChildren() {
        return this.children;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean equals(Object pOther) {
        if (this == pOther) {
            return true;
        } else if (!(pOther instanceof Skill)) {
            return false;
        } else {
            Skill skill = (Skill) pOther;
            return this.id.equals(skill.id);
        }
    }

    public void addChild(Skill pChild) {
        this.children.add(pChild);
    }


    public DisplayInfo getDisplay() {
        return this.display;
    }


}
