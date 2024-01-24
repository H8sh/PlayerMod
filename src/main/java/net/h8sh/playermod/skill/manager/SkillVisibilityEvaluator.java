package net.h8sh.playermod.skill.manager;

import it.unimi.dsi.fastutil.Stack;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.function.Predicate;

import net.h8sh.playermod.skill.Skill;
import net.minecraft.advancements.DisplayInfo;

public class SkillVisibilityEvaluator {
    private static final int VISIBILITY_DEPTH = 2;

    private static SkillVisibilityEvaluator.VisibilityRule evaluateVisibilityRule(Skill skill, boolean pAlwaysShow) {
        DisplayInfo displayinfo = skill.getDisplay();
        if (displayinfo == null) {
            return SkillVisibilityEvaluator.VisibilityRule.HIDE;
        } else if (pAlwaysShow) {
            return SkillVisibilityEvaluator.VisibilityRule.SHOW;
        } else {
            return displayinfo.isHidden() ? SkillVisibilityEvaluator.VisibilityRule.HIDE : SkillVisibilityEvaluator.VisibilityRule.NO_CHANGE;
        }
    }

    private static boolean evaluateVisiblityForUnfinishedNode(Stack<SkillVisibilityEvaluator.VisibilityRule> pVisibilityRules) {
        for(int i = 0; i <= 2; ++i) {
            SkillVisibilityEvaluator.VisibilityRule skillvisibilityevaluator$visibilityrule = pVisibilityRules.peek(i);
            if (skillvisibilityevaluator$visibilityrule == SkillVisibilityEvaluator.VisibilityRule.SHOW) {
                return true;
            }

            if (skillvisibilityevaluator$visibilityrule == SkillVisibilityEvaluator.VisibilityRule.HIDE) {
                return false;
            }
        }

        return false;
    }

    private static boolean evaluateVisibility(Skill skill, Stack<SkillVisibilityEvaluator.VisibilityRule> pVisibilityRules, Predicate<Skill> pPredicate, SkillVisibilityEvaluator.Output pOutput) {
        boolean flag = pPredicate.test(skill);
        SkillVisibilityEvaluator.VisibilityRule skillvisibilityevaluator$visibilityrule = evaluateVisibilityRule(skill, flag);
        boolean flag1 = flag;
        pVisibilityRules.push(skillvisibilityevaluator$visibilityrule);

        for(Skill skill1 : skill.getChildren()) {
            flag1 |= evaluateVisibility(skill1, pVisibilityRules, pPredicate, pOutput);
        }

        boolean flag2 = flag1 || evaluateVisiblityForUnfinishedNode(pVisibilityRules);
        pVisibilityRules.pop();
        pOutput.accept(skill, flag2);
        return flag1;
    }

    public static void evaluateVisibility(Skill skill, Predicate<Skill> pPredicate, SkillVisibilityEvaluator.Output pOutput) {
        Skill skillRoot = skill.getRoot();
        Stack<SkillVisibilityEvaluator.VisibilityRule> stack = new ObjectArrayList<>();

        for(int i = 0; i <= 2; ++i) {
            stack.push(SkillVisibilityEvaluator.VisibilityRule.NO_CHANGE);
        }

        evaluateVisibility(skillRoot, stack, pPredicate, pOutput);
    }

    public static boolean isVisible(Skill skill, Predicate<Skill> test) {
        Stack<SkillVisibilityEvaluator.VisibilityRule> stack = new ObjectArrayList<>();

        for(int i = 0; i <= 2; ++i) {
            stack.push(SkillVisibilityEvaluator.VisibilityRule.NO_CHANGE);
        }
        return evaluateVisibility(skill.getRoot(), stack, test, (skill1, pVisible) -> {});
    }

    @FunctionalInterface
    public interface Output {
        void accept(Skill skill, boolean pVisible);
    }

    static enum VisibilityRule {
        SHOW,
        HIDE,
        NO_CHANGE;
    }
}
