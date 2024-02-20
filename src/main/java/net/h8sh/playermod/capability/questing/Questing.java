package net.h8sh.playermod.capability.questing;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.HashMap;

@AutoRegisterCapability
public class Questing {
    private static HashMap<Integer, Boolean> quests = new HashMap<>();

    private static void setQuests() {
        QuestBuilder.buildQuests().forEach(questBuilder -> {
            int id = questBuilder.getId();
            boolean flag = questBuilder.getFlag();
            quests.putIfAbsent(id, flag);
        });
    }

    public static void validateQuest(int id) {
        quests.put(id, true);
    }

    public void copyFrom(Questing source) {
        this.quests = source.quests;
    }

    public void saveNBTData(CompoundTag compound) {
        ListTag list = new ListTag();
        quests.forEach((id, flag) -> {
            CompoundTag questTag = new CompoundTag();
            questTag.putInt("id", id);
            questTag.putBoolean("flag", flag);
            list.add(questTag);
        });
        compound.put("quest", list);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        ListTag list = compoundTag.getList("quest", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag questTag = (CompoundTag) t;
            int id = questTag.getInt("id");
            boolean flag = questTag.getBoolean("flag");
            quests.put(id, flag);
        }
    }
}
