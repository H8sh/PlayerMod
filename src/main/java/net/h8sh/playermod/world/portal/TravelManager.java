package net.h8sh.playermod.world.portal;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class TravelManager extends SavedData {

    private final Map<String, BlockPos> travelMap = new HashMap<>();


    public TravelManager() {
    }

    public TravelManager(CompoundTag tag) {
        ListTag list = tag.getList("travel", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag travelTag = (CompoundTag) t;
            String travel = travelTag.getString("travel");
            BlockPos pos = new BlockPos(travelTag.getInt("x"), travelTag.getInt("y"), travelTag.getInt("z"));
            travelMap.put(travel, pos);
        }
    }

    @Nonnull
    public static TravelManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel) level).getDataStorage();
        return storage.computeIfAbsent(TravelManager::new, TravelManager::new, "travel_manager");
    }

    public static void teleport(ServerPlayer entity, ServerLevel destination, BlockPos pos, boolean findTop) {
        entity.changeDimension(destination, new ITeleporter() {
            @Override
            public Entity placeEntity(Entity entity, ServerLevel currentWorld, ServerLevel destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                entity = repositionEntity.apply(false);
                int y = pos.getY();
                /*if (findTop) {
                    y = destination.getChunk(SectionPos.blockToSectionCoord(pos.getX()),
                            SectionPos.blockToSectionCoord(pos.getZ())).getHeight(Heightmap.Types.WORLD_SURFACE_WG,
                            pos.getX() & 15, pos.getZ() & 15) + 1;
                }*/
                entity.teleportTo(pos.getX(), y, pos.getZ());
                return entity;
            }
        });
    }

    public static void createPortal(ServerLevel world, BlockPos pos) {

        StructureTemplate template = world.getStructureManager().getOrCreate(new ResourceLocation(PlayerMod.MODID, "wonderlands_home"));
        template.placeInWorld(world, pos, pos, new StructurePlaceSettings(), world.getRandom(), Block.UPDATE_ALL);

    }

    public static void teleportTo(ServerPlayer player, BlockPos pos, ResourceKey<Level> id) {
        ServerLevel world = player.getServer().getLevel(id);
        teleport(player, world, new BlockPos(pos.getX(), pos.getY(), pos.getZ()), true);
    }

    public boolean asAlreadyTravel(String name) {
        return travelMap.containsKey(name);
    }

    public void usedToTravel(String name, BlockPos pos) {
        travelMap.put(name, pos);
    }

    public BlockPos getPos(String name) {
        return travelMap.get(name);
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        travelMap.forEach((travel, pos) -> {
            CompoundTag travelTag = new CompoundTag();
            travelTag.putString("travel", travel);
            travelTag.putInt("x", pos.getX());
            travelTag.putInt("y", pos.getY());
            travelTag.putInt("z", pos.getZ());
            list.add(travelTag);
        });
        tag.put("travel", list);
        return tag;
    }


}
