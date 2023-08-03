package net.h8sh.playermod.ability.wizard.aoe;

import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.wizard.aoepacket.MagicAoES2CPacket;
import net.minecraft.client.Minecraft;
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
import net.minecraft.world.phys.HitResult;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class MagicAoEManager extends SavedData {
    private final static String AOE_TAG = "AoE";

    private static int counter = 0;
    private static BlockPos pos;
    private static BlockPos currentPos;
    private Map<BlockPos, MagicAoE> AoEMap = new HashMap<>();

    public MagicAoEManager() {
    }

    public MagicAoEManager(CompoundTag tag) {
        ListTag list = tag.getList(AOE_TAG, Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag AoETag = (CompoundTag) t;
            MagicAoE AoE = new MagicAoE(AoETag.getInt(AOE_TAG));
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
        return storage.computeIfAbsent(MagicAoEManager::new, MagicAoEManager::new, "aoe_manager");
    }

    public static void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 10;

            // TODO AoE visualization: client-only and re-use the same entity (spawn it once, then move it, destroy it at the end).

            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    //TODO: change CrystalEntity to MagicAoEEntity && save current pos
                    pos = getBlockLookAt();
                    if (pos != currentPos) {
                        //TODO: kill aoe
                        ModMessages.sendToServer(new MagicAoES2CPacket());
                        currentPos = pos;
                        //TODO: spawn new aoe
                        ModEntities.CRYSTAL.get().spawn((ServerLevel) serverPlayer.level(), null, serverPlayer, pos, MobSpawnType.COMMAND, true, false);
                    }
                }
            });
        }
    }

    private static BlockPos getBlockLookAt() {

        HitResult rt = Minecraft.getInstance().hitResult;

        double x = (rt.getLocation().x);
        double y = (rt.getLocation().y);
        double z = (rt.getLocation().z);

        double xla = Minecraft.getInstance().player.getLookAngle().x;
        double yla = Minecraft.getInstance().player.getLookAngle().y;
        double zla = Minecraft.getInstance().player.getLookAngle().z;

        if ((x % 1 == 0) && (xla < 0)) x -= 0.01;
        if ((y % 1 == 0) && (yla < 0)) y -= 0.01;
        if ((z % 1 == 0) && (zla < 0)) z -= 0.01;

        return new BlockPos((int) x, (int) y, (int) z);

    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listAoE = new ListTag();
        AoEMap.forEach((key, value) -> {
            CompoundTag AoETag = new CompoundTag();
            AoETag.putInt("x", key.getX());
            AoETag.putInt("y", key.getY());
            AoETag.putInt("z", key.getZ());
            AoETag.putInt(AOE_TAG, value.getAoE());
            listAoE.add(AoETag);
        });
        tag.put(AOE_TAG, listAoE);
        return tag;
    }

}
