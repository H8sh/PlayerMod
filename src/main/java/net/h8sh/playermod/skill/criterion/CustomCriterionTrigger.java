package net.h8sh.playermod.skill.criterion;

import com.google.gson.JsonObject;
import net.h8sh.playermod.skill.PlayerSkills;
import net.h8sh.playermod.skill.Skill;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public interface CustomCriterionTrigger<T extends CriterionTriggerInstance> {
    ResourceLocation getId();

    void addPlayerListener(PlayerSkills pPlayerAdvancements, CustomCriterionTrigger.Listener<T> pListener);

    void removePlayerListener(PlayerSkills pPlayerAdvancements, CustomCriterionTrigger.Listener<T> pListener);

    void removePlayerListeners(PlayerSkills pPlayerAdvancements);

    T createInstance(JsonObject pJson, DeserializationContext pContext);

    public static class Listener<T extends CriterionTriggerInstance> {
        private final T trigger;
        private final Skill advancement;
        private final String criterion;

        public Listener(T pTrigger, Skill pAdvancement, String pCriterion) {
            this.trigger = pTrigger;
            this.advancement = pAdvancement;
            this.criterion = pCriterion;
        }

        public T getTriggerInstance() {
            return this.trigger;
        }

        public void run(PlayerSkills pPlayerAdvancements) {
            pPlayerAdvancements.award(this.advancement, this.criterion);
        }

        public boolean equals(Object pOther) {
            if (this == pOther) {
                return true;
            } else if (pOther != null && this.getClass() == pOther.getClass()) {
                CustomCriterionTrigger.Listener<?> listener = (CustomCriterionTrigger.Listener)pOther;
                if (!this.trigger.equals(listener.trigger)) {
                    return false;
                } else {
                    return !this.advancement.equals(listener.advancement) ? false : this.criterion.equals(listener.criterion);
                }
            } else {
                return false;
            }
        }

        public int hashCode() {
            int i = this.trigger.hashCode();
            i = 31 * i + this.advancement.hashCode();
            return 31 * i + this.criterion.hashCode();
        }
    }
}