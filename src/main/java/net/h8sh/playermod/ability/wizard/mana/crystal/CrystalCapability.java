package net.h8sh.playermod.ability.wizard.mana.crystal;

import net.h8sh.playermod.ability.wizard.mana.ManaCapability;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import static java.lang.Thread.sleep;

@AutoRegisterCapability
public class CrystalCapability {
    private static HashMap<BlockPos, Integer> crystalMap = new HashMap<>();
    private int crystal;
    private int timer;

    public static UUID spawnCrystal(BlockPos blockPos, ServerLevel level) {
        CrystalEntity crystalEntity = ModEntities.CRYSTAL.get().spawn(level, new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), MobSpawnType.SPAWNER);
        crystalMap.put(blockPos,1);
        return crystalEntity.getUUID();
    }

    public static boolean isCrystal(BlockPos blockPos) {
        return crystalMap.get(blockPos) == null;
    }

    public static void setTimer(BlockPos blockPos, ServerLevel level, UUID uuid, ServerPlayer player) {
        final int[] tickTimer = {20};
        new Thread(() -> {
            while (tickTimer[0] != 0) {
                tickTimer[0]--;
                Entity entity = level.getEntity(uuid);
                entity.setCustomName(Component.literal("" + Arrays.toString(tickTimer)));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            level.getEntity(uuid).kill();
            ManaCapability.setManaInChunk(level.getChunkAt(blockPos).getPos());
        }).start();
    }

    public int getPlayerCrystal() {
        return crystal;
    }

    public void setPlayerCrystal(int playerCrystal) {
        this.crystal = playerCrystal;
    }

    public int getCrystal() {
        return crystal;
    }

    public void setCrystal(int crystal) {
        this.crystal = crystal;
    }

    public void addCrystal(int crystal) {
        this.crystal += crystal;
    }

    public void removeCrystal(int crystal) {
        this.crystal -= crystal;
    }

    public void copyFrom(CrystalCapability source) {
        this.crystal = source.crystal;
    }

    public void savedNBTData(CompoundTag compound) {
        compound.putInt("crystal", crystal);
        compound.putInt("timer", timer);

        ListTag list = new ListTag();
        crystalMap.forEach((blockPos, timer) -> {
            CompoundTag crystalTag = new CompoundTag();
            crystalTag.putInt("x", blockPos.getX());
            crystalTag.putInt("y", blockPos.getY());
            crystalTag.putInt("z", blockPos.getZ());
            crystalTag.putInt("crystal", timer);
            list.add(crystalTag);
        });
        compound.put("crystal", list);

    }

    public void loadNBTData(CompoundTag compoundTag) {
        ListTag list = compoundTag.getList("crystal", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag crystalTag = (CompoundTag) t;
            int timer = crystalTag.getInt("crystal");
            BlockPos pos = new BlockPos(crystalTag.getInt("x"), crystalTag.getInt("y"), crystalTag.getInt("z"));
            crystalMap.put(pos, timer);
        }

        crystal = compoundTag.getInt("crystal");
        timer = compoundTag.getInt("timer");

    }

}