package net.h8sh.playermod.capability.questing;

import net.h8sh.playermod.capability.questing.reader.Quest;
import net.h8sh.playermod.capability.questing.reader.Quests;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class QuestBuilder {

    private int id;

    private boolean flag;

    private ResourceLocation texture;

    private String description;

    private String name;

    private int parentId;

    private QuestBuilder(int id, boolean flag, ResourceLocation texture, String description, String name, int parentId) {
        this.id = id;
        this.flag = flag;
        this.texture = texture;
        this.description = description;
        this.name = name;
        this.parentId = parentId;
    }

    public static QuestBuilder getQuestFromId(int id) {
        return buildQuests().get(id);
    }

    private static <T> List<T> joinLists(List<T>... lists) {
        return Arrays.stream(lists).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public static List<QuestBuilder> buildQuests() {
        var mainQuests = buildMainQuests();
        var otherQuests = buildOtherQuests();
        return joinLists(mainQuests, otherQuests);
    }

    private static List<QuestBuilder> buildMainQuests() {
        List<QuestBuilder> quests = new ArrayList<>();
        var mainQuests = Quests.getAllQuests().getQuests().get(QuestsTypes.MAIN_QUESTS.getId()).getMainQuest();
        for (Quest quest : mainQuests) {
            QuestBuilder questBuilder = new QuestBuilder(quest.getId(),
                    false,
                    quest.getTexture(),
                    quest.getDescription(),
                    quest.getName(),
                    quest.getParentId());
            quests.add(questBuilder);
        }
        return quests;
    }

    private static List<QuestBuilder> buildOtherQuests() {
        List<QuestBuilder> quests = new ArrayList<>();
        var otherQuests = Quests.getAllQuests().getQuests().get(QuestsTypes.OTHER_QUESTS.getId()).getOtherQuest();
        for (Quest quest : otherQuests) {
            QuestBuilder questBuilder = new QuestBuilder(quest.getId(),
                    false,
                    quest.getTexture(),
                    quest.getDescription(),
                    quest.getName(),
                    quest.getParentId());
            quests.add(questBuilder);
        }
        return quests;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public String getDescription() {
        return description;
    }

    public int getParentId() {
        return parentId;
    }

    public boolean getFlag() {
        return flag;
    }

    private enum QuestsTypes {
        MAIN_QUESTS(0, "main_quests"),
        OTHER_QUESTS(1, "other_quests");

        private final int id;
        private final String name;

        QuestsTypes(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
