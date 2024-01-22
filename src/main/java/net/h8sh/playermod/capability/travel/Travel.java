package net.h8sh.playermod.capability.travel;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.ITeleporter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AutoRegisterCapability
public class Travel {
    private final Map<String, BlockPos> travelMap = new HashMap<>();

    public static void teleportToDimension(String dimension, ServerPlayer player, ServerLevel newWorld) {
        player.getCapability(TravelProvider.PLAYER_TRAVEL).ifPresent(travel -> {
            Travel.teleport(player, newWorld, new BlockPos(0, 50, 0), true);

            if (!travel.asAlreadyTravel(dimension)) {

                Travel.createPortal(newWorld,
                        new BlockPos(player.blockPosition().getX() - 2,
                                player.blockPosition().getY() - 1,
                                player.blockPosition().getZ() - 2));

                player.teleportTo(player.position().x + 0.5, player.position().y, player.position().z + 0.5);

                player.sendSystemMessage(Component.literal("Here for the first time").withStyle(ChatFormatting.AQUA));

                travel.usedToTravel(dimension, player.blockPosition());

            } else {

                BlockPos pos = travel.getPos(dimension);

                player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5); //BlockPos cast player coords to integers so we have to add 0.5 again for centering the player

                player.sendSystemMessage(Component.literal("Already been there").withStyle(ChatFormatting.RED));
            }
        });
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

    public void copyFrom(Travel source) {

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

    public void savedNBTData(CompoundTag compound) {
        ListTag list = new ListTag();
        travelMap.forEach((travel, pos) -> {
            CompoundTag travelTag = new CompoundTag();
            travelTag.putString("travel", travel);
            travelTag.putInt("x", pos.getX());
            travelTag.putInt("y", pos.getY());
            travelTag.putInt("z", pos.getZ());
            list.add(travelTag);
        });
        compound.put("travel", list);

    }

    public void loadNBTData(CompoundTag compoundTag) {
        ListTag list = compoundTag.getList("travel", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag travelTag = (CompoundTag) t;
            String travel = travelTag.getString("travel");
            BlockPos pos = new BlockPos(travelTag.getInt("x"), travelTag.getInt("y"), travelTag.getInt("z"));
            travelMap.put(travel, pos);
        }
    }

}
