package net.h8sh.playermod.ability.wizard.aoe;

import net.h8sh.playermod.ability.networking.AbilityMessages;
import net.h8sh.playermod.ability.networking.wizard.aoepacket.PacketMagicAoE;
import net.h8sh.playermod.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MagicAoEManager extends SavedData {

    private static int counter = 0;
    private static float deltaRadius;
    private static float blocLookAtX;
    private static float blocLookAtZ;
    private static float deltaAngle;
    private static BlockPos pos;
    private static BlockPos currentPos;
    private Map<BlockPos, MagicAoE> AoEMap = new HashMap<>();

    public MagicAoEManager() {
    }

    public MagicAoEManager(CompoundTag tag) {
        ListTag list = tag.getList("AoE", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag AoETag = (CompoundTag) t;
            MagicAoE AoE = new MagicAoE(AoETag.getInt("AoE"));
            BlockPos pos = new BlockPos(AoETag.getInt("x"), AoETag.getInt("y"), AoETag.getInt("z"));
            AoEMap.put(pos, AoE);
        }
    }

    @NonNull
    public static MagicAoEManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this cleint side");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(MagicAoEManager::new, MagicAoEManager::new, "manager");
    }

    public static void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 10;
            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    //TODO: change CrystalEntity to MagicAoEEntity && save current pos
                    pos = getBlockLookAt(serverPlayer);
                    if (pos != currentPos) {
                        //TODO: kill aoe
                        AbilityMessages.sendToServer(new PacketMagicAoE());
                        currentPos = pos;
                        //TODO: spawn new aoe
                        ModEntities.CRYSTAL.get().spawn((ServerLevel) serverPlayer.level(), null, serverPlayer, pos, MobSpawnType.COMMAND, true, false);
                    }
                }
            });
        }
    }

    public static BlockPos getPos() {
        return pos;
    }

    private static BlockPos getBlockLookAt(ServerPlayer serverPlayer) {

        float xRot = (float) (serverPlayer.getXRot() * (Math.PI / 180));
        float yHeadRot = (float) (serverPlayer.getYHeadRot() * Math.PI / 180);
        if (yHeadRot < 0) {
            deltaRadius = (float) (Math.tan(Math.PI / 2 - xRot) * serverPlayer.getEyeHeight());
            deltaAngle = (float) (Math.PI / 2 + yHeadRot);
            blocLookAtX = (float) (serverPlayer.position().x + deltaRadius * Math.cos(deltaAngle));
        } else if (yHeadRot > 0) {
            deltaRadius = (float) (Math.tan(Math.PI / 2 - xRot) * serverPlayer.getEyeHeight());
            deltaAngle = (float) (Math.PI / 2 - yHeadRot);
            blocLookAtX = (float) (serverPlayer.position().x - deltaRadius * Math.cos(deltaAngle));
        }

        blocLookAtZ = (float) (serverPlayer.position().z + deltaRadius * Math.sin(deltaAngle));

        return new BlockPos((int) blocLookAtX, (int) serverPlayer.position().y, (int) blocLookAtZ);
    }

    @NotNull
    private MagicAoE setNewMagicAoEInternal(BlockPos pos) {
        return AoEMap.computeIfAbsent(pos, cp -> new MagicAoE(0));
    }

    public int setMagicAoEHashMapToZero(BlockPos pos) {
        MagicAoE magicAoE = setNewMagicAoEInternal(pos);
        return magicAoE.getAoE();
    }

    public MagicAoE setMagicAoEFlag(BlockPos pos) {

        return AoEMap.put(pos, new MagicAoE(1));
    }

    public MagicAoE getMagicAoE(BlockPos pos) {

        return AoEMap.get(pos);
    }

    public int extractAoE(BlockPos pos) {
        MagicAoE magicAoE = setNewMagicAoEInternal(pos);
        int present = magicAoE.getAoE();
        magicAoE.setAoE(present + 1);
        setDirty();
        return 1;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listAoE = new ListTag();
        AoEMap.forEach((key, value) -> {
            CompoundTag AoETag = new CompoundTag();
            AoETag.putInt("x", key.getX());
            AoETag.putInt("y", key.getY());
            AoETag.putInt("z", key.getZ());
            AoETag.putInt("AoE", value.getAoE());
            listAoE.add(AoETag);
        });
        tag.put("AoE", listAoE);
        return tag;
    }

}
