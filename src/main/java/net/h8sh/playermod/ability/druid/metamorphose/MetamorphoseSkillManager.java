package net.h8sh.playermod.ability.druid.metamorphose;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class MetamorphoseSkillManager extends SavedData {
    private Map<BlockPos, MetamorphoseSkill> metamorphoseMap = new HashMap<>();

    public MetamorphoseSkillManager() {
    }

    public MetamorphoseSkillManager(CompoundTag tag) {
        ListTag list = tag.getList("metamorphose_skill", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag metamorphoseTag = (CompoundTag) t;
            MetamorphoseSkill metamorphoseSkill = new MetamorphoseSkill(metamorphoseTag.getInt("metamorphose"));
            BlockPos pos = new BlockPos(metamorphoseTag.getInt("x"), metamorphoseTag.getInt("y"), metamorphoseTag.getInt("z"));
            metamorphoseMap.put(pos, metamorphoseSkill);
        }
    }

    @NonNull
    public static MetamorphoseSkillManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this cleint side");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(MetamorphoseSkillManager::new, MetamorphoseSkillManager::new, "metamorphose_manager");
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listMetamorphose = new ListTag();
        metamorphoseMap.forEach((key, value) -> {
            CompoundTag metamorphoseTag = new CompoundTag();
            metamorphoseTag.putInt("x", key.getX());
            metamorphoseTag.putInt("y", key.getY());
            metamorphoseTag.putInt("z", key.getZ());
            metamorphoseTag.putInt("metamorphose", value.getMetamorphoseSkill());
            listMetamorphose.add(metamorphoseTag);
        });
        tag.put("metamorphose_skill", listMetamorphose);
        return tag;
    }

}
