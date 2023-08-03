package net.h8sh.playermod.ability.wizard.mana.crystal;

import net.h8sh.playermod.ability.wizard.mana.crystal.utils.CrystalNameTag;
import net.h8sh.playermod.capability.ability.wizard.mana.crystal.CrystalCapability;
import net.h8sh.playermod.capability.ability.wizard.mana.crystal.CrystalCapabilityProvider;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.wizard.manapacket.crystal.SyncCrystalToClientC2SPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.phys.AABB;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrystalManager extends SavedData {

    private final Map<ChunkPos, Crystal> crystalMap = new HashMap<>();

    private int counter = 0;

    public CrystalManager() {
    }

    public CrystalManager(CompoundTag tag) {
        ListTag list = tag.getList("crystal", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag crystalTag = (CompoundTag) t;
            Crystal crystal = new Crystal(crystalTag.getInt("crystal"));
            ChunkPos pos = new ChunkPos(crystalTag.getInt("x"), crystalTag.getInt("z"));
            crystalMap.put(pos, crystal);
        }
    }

    @NonNull
    public static CrystalManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this cleint side");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(CrystalManager::new, CrystalManager::new, "crystal");
    }

    public static List<Entity> removeLivingCrystals(Player player, Level world) {
        AABB overlordBoundingBox = new AABB(
                world.getWorldBorder().getMinX(),
                -128,
                world.getWorldBorder().getMinZ(),
                world.getWorldBorder().getMaxX(),
                128,
                world.getWorldBorder().getMaxZ()
        );
        List<Entity> crystals = new ArrayList<>();
        List<Entity> entities = new ArrayList<>();
        world.getEntities(player, overlordBoundingBox, Entity::isAlive).forEach(entities::add);
        for (Entity entity : entities) {
            if (entity instanceof CrystalEntity) {
                crystals.add(entity);
            }
        }
        return crystals;
    }

    @NotNull
    private Crystal setNewCrystalInternal(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return crystalMap.computeIfAbsent(chunkPos, cp -> new Crystal(0));
    }

    public int setCrystalHashMapToZero(BlockPos pos) {
        Crystal crystal = setNewCrystalInternal(pos);
        return crystal.getCrystal();
    }

    public Crystal setCrystalFlag(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return crystalMap.put(chunkPos, new Crystal(1));
    }

    public Crystal getCrystal(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return crystalMap.get(chunkPos);
    }

    public int extractCrystal() {
        return 1;
    }

    public int reduceCrystal(BlockPos pos) {
        Crystal crystal = setNewCrystalInternal(pos);
        int present = crystal.getCrystal();
        crystal.setCrystal(present - 1);
        setDirty();
        return 1;
    }

    public CrystalEntity spawnCrystal(BlockPos blockPosition, ServerPlayer serverPlayer, ServerLevel serverLevel) {

        //Set pos of crystal at center of chunk
        ChunkPos chunkPos = new ChunkPos(blockPosition);
        int x = chunkPos.getMiddleBlockX();
        int z = chunkPos.getMiddleBlockZ();
        int y = (int) (serverPlayer.getEyeY());
        BlockPos pos = new BlockPos(x, y, z);

        //set crystal + properties
        CrystalEntity crystal = (CrystalEntity) ModEntities.CRYSTAL.get().spawn(serverLevel, null, serverPlayer, pos, MobSpawnType.COMMAND, true, false);
        crystal.setNoGravity(true);
        crystal.setInvulnerable(true);
        crystal.setCustomNameVisible(true); // name visible

        //add custom name tag + delay
        CrystalNameTag crystalNameTag = new CrystalNameTag(crystal);
        crystalNameTag.start();

        return crystal;
    }

    public void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 10;
            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    int playerCrystal = serverPlayer.getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).map(CrystalCapability::getCrystal).orElse(-1);

                    ModMessages.sendToPlayer(new SyncCrystalToClientC2SPacket(playerCrystal), serverPlayer);
                }
            });
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listCrystal = new ListTag();
        crystalMap.forEach((key, value) -> {
            CompoundTag crystalTag = new CompoundTag();
            crystalTag.putInt("x", key.x);
            crystalTag.putInt("z", key.z);
            crystalTag.putInt("crystal", value.getCrystal());
            listCrystal.add(crystalTag);
        });
        tag.put("crystal", listCrystal);
        return tag;
    }


}