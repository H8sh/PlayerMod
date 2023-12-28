package net.h8sh.playermod.ability.wizard.mana;

import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.wizard.mana.PacketSyncManaToClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Random;

public class ManaCapability {

    private static int MAX_MANA = 500;
    private static int MIN_MANA = 200;
    private static HashMap<ChunkPos, Integer> manaMap = new HashMap<>();
    private static Random random = new Random();
    private int playerMana;

    public static int getManaInChunk(ChunkPos chunkPos) {
        int chunkMana;
        if (!manaMap.containsKey(chunkPos)) {

            chunkMana = random.nextInt(MAX_MANA - MIN_MANA + 1) + MIN_MANA;
            manaMap.put(chunkPos, chunkMana);
        } else {
            chunkMana = manaMap.get(chunkPos);
        }
        return chunkMana;
    }

    public static void setManaInChunk(ChunkPos chunkPos) {
        manaMap.put(chunkPos, setRandomMana());
    }

    private static Integer setRandomMana() {
        return random.nextInt(MAX_MANA - MIN_MANA + 1) + MIN_MANA;
    }

    public static void tick(Level level) {
        level.players().forEach(player -> {
            if (player instanceof ServerPlayer serverPlayer) {
                int playerMana = serverPlayer.getCapability(ManaCapabilityProvider.PLAYER_MANA)
                        .map(ManaCapability::getPlayerMana)
                        .orElse(-1);
                int chunkMana = getManaInChunk(player.level().getChunkAt(player.blockPosition()).getPos());
                ModMessages.sendToPlayer(new PacketSyncManaToClient(playerMana, chunkMana), serverPlayer);
            }
        });
    }

    public static int extractMana(ChunkPos chunkPos) {
        int mana = manaMap.get(chunkPos);
        if (mana > 0) {
            manaMap.put(chunkPos, mana - 1);
            return 1;
        } else {
            return 0;
        }
    }

    public int getPlayerMana() {
        return playerMana;
    }

    public void setPlayerMana(int playerMana) {
        this.playerMana = playerMana;
    }

    public void copyFrom(ManaCapability source) {
        this.playerMana = source.playerMana;
    }

    public void savedNBTData(CompoundTag compound) {
        compound.putInt("playerMana", playerMana);

        ListTag list = new ListTag();
        manaMap.forEach((chunkPos, mana) -> {
            CompoundTag manaTag = new CompoundTag();
            manaTag.putInt("x", chunkPos.x);
            manaTag.putInt("z", chunkPos.z);
            manaTag.putInt("mana", mana);
            list.add(manaTag);
        });
        compound.put("mana", list);

    }

    public void loadNBTData(CompoundTag compoundTag) {
        ListTag list = compoundTag.getList("mana", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag manaTag = (CompoundTag) t;
            int mana = manaTag.getInt("mana");
            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));
            manaMap.put(pos, mana);
        }

        playerMana = compoundTag.getInt("playerMana");

    }

}
