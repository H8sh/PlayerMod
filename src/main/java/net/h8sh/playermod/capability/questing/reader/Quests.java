package net.h8sh.playermod.capability.questing.reader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Quests {

    private static Quests QUESTS;

    @SerializedName("QuestBuilder")
    public List<QuestTypes> quests;

    public static void setQuestsFromJson(Quests quests) {
        QUESTS = quests;
    }

    public static Quests getAllQuests() {
        return QUESTS;
    }

    public List<QuestTypes> getQuests() {
        return quests;
    }

    public void setQuests(List<QuestTypes> quests) {
        this.quests = quests;
    }

}
