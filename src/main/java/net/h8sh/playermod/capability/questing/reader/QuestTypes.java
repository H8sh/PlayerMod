package net.h8sh.playermod.capability.questing.reader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestTypes {

    @SerializedName("MainQuests")
    public List<Quest> mainQuest;
    @SerializedName("OtherQuests")
    public List<Quest> otherQuest;

    public List<Quest> getMainQuest() {
        return mainQuest;
    }

    public List<Quest> getOtherQuest() {
        return otherQuest;
    }

}
