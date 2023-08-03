package net.h8sh.playermod.ability.wizard.mana;

import net.h8sh.playermod.capability.ability.wizard.mana.ManaCapability;
import net.h8sh.playermod.capability.ability.wizard.mana.ManaCapabilityProvider;
import net.h8sh.playermod.config.WonderlandsModServerConfigs;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.wizard.manapacket.SyncManaToClientS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ManaManager extends SavedData {

    private final Map<ChunkPos, Mana> manaMap = new HashMap<>();
    private Random random = new Random();

    private int counter = 0;

    public ManaManager() {
    }

    public ManaManager(CompoundTag tag) {
        ListTag list = tag.getList("mana", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag manaTag = (CompoundTag) t;
            Mana mana = new Mana(manaTag.getInt("mana"));
            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));
            manaMap.put(pos, mana);

        }
    }

    @NonNull
    public static ManaManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this cleint side");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "mana_manager");
    }

    @NotNull
    public void setManaInternal(BlockPos blockPos) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        manaMap.put(chunkPos, new Mana(random.nextInt(WonderlandsModServerConfigs.CHUNK_MAX_MANA.get()) + WonderlandsModServerConfigs.CHUNK_MIN_MANA.get()));
    }

    @NotNull
    private Mana getManaInternal(BlockPos blockPos) {
        ChunkPos chunkPos = new ChunkPos(blockPos);
        return manaMap.computeIfAbsent(chunkPos, cp -> new Mana(random.nextInt(WonderlandsModServerConfigs.CHUNK_MAX_MANA.get()) + WonderlandsModServerConfigs.CHUNK_MIN_MANA.get()));
    }

    public int getMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        return mana.getMana();
    }

    public int extractMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        int present = mana.getMana();
        if (present > 0) {
            mana.setMana(present - 1);
            setDirty();
            return 1;
        } else {
            return 0;
        }
    }

    public void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 10;
            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    int playerMana = serverPlayer.getCapability(ManaCapabilityProvider.PLAYER_MANA)
                            .map(ManaCapability::getMana)
                            .orElse(-1);

                    int chunkMana = getMana(serverPlayer.blockPosition());

                    ModMessages.sendToPlayer(new SyncManaToClientS2CPacket(playerMana, chunkMana), serverPlayer);
                }
            });
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listMana = new ListTag();
        manaMap.forEach((key, value) -> {
            CompoundTag manaTag = new CompoundTag();
            manaTag.putInt("x", key.x);
            manaTag.putInt("z", key.z);
            manaTag.putInt("mana", value.getMana());
            listMana.add(manaTag);
        });
        tag.put("mana", listMana);
        return tag;
    }
}