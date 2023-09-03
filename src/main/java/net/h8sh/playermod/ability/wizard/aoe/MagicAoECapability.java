package net.h8sh.playermod.ability.wizard.aoe;

import net.h8sh.playermod.block.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class MagicAoECapability {

    private static boolean prepareAoe;
    private static boolean readyToUse;
    private int X0;
    private int Y0;
    private int Z0;

    private int X1;
    private int Y1;
    private int Z1;


    private int X2;
    private int Y2;
    private int Z2;


    private int X3;
    private int Y3;
    private int Z3;


    private int X4;
    private int Y4;
    private int Z4;

    public static void setPrepareAoe(boolean prepareAoe) {
        MagicAoECapability.prepareAoe = prepareAoe;
    }
    public static boolean getPrepareAoe() {
        return MagicAoECapability.prepareAoe;
    }

    public static void setReadyToUse(boolean readyToUse) {
        MagicAoECapability.readyToUse = readyToUse;
    }
    public static boolean getReadyToUse() {
        return MagicAoECapability.readyToUse;
    }

    public static Vec3 getBlockLookAt() {
        Player player = Minecraft.getInstance().player;
        HitResult hitResult = player.pick(10, Minecraft.getInstance().getPartialTick(), false);
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getLocation();
        } else {
            return new Vec3(player.getX(), player.getY(), player.getZ());
        }
    }

    public static BlockPos getTopNonCollidingPos(Level pLevel, EntityType<?> pEntityType, int pX, int pZ) {
        int i = pLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pX, pZ);
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(pX, i, pZ);
        if (pLevel.dimensionType().hasCeiling()) {
            do {
                blockpos$mutableblockpos.move(Direction.DOWN);
            } while (!pLevel.getBlockState(blockpos$mutableblockpos).isAir());

            do {
                blockpos$mutableblockpos.move(Direction.DOWN);
            } while (pLevel.getBlockState(blockpos$mutableblockpos).isAir() && blockpos$mutableblockpos.getY() > pLevel.getMinBuildHeight());
        }

        if (SpawnPlacements.getPlacementType(pEntityType) == SpawnPlacements.Type.ON_GROUND) {
            BlockPos blockpos = blockpos$mutableblockpos.below();
            if (pLevel.getBlockState(blockpos).isPathfindable(pLevel, blockpos, PathComputationType.LAND)) {
                return blockpos;
            }
        }

        return blockpos$mutableblockpos.immutable();
    }

    public void spawnAoeMarker(Level level) {
        List<BlockPos> aoe = createAoe(level);
        for (BlockPos blockPos : aoe) {
            level.setBlock(blockPos, ModBlocks.AOE_MARKER_BLOCK.get().defaultBlockState(), 1);
        }
    }

    public void spawnAoe(Level level) {
        List<BlockPos> aoe = createAoe(level);
        for (BlockPos blockPos : aoe) {
            level.removeBlock(blockPos, false);
            level.setBlock(blockPos, ModBlocks.AOE_BLOCK.get().defaultBlockState(), 1);
        }
    }

    public void eraseOldAoe(Level level) {
        BlockPos pos0 = new BlockPos(this.X0, this.Y0, this.Z0);
        level.removeBlock(pos0, false);
        BlockPos pos1 = new BlockPos(this.X1, this.Y1, this.Z1);
        level.removeBlock(pos1, false);
        BlockPos pos2 = new BlockPos(this.X2, this.Y2, this.Z2);
        level.removeBlock(pos2, false);
        BlockPos pos3 = new BlockPos(this.X3, this.Y3, this.Z3);
        level.removeBlock(pos3, false);
        BlockPos pos4 = new BlockPos(this.X4, this.Y4, this.Z4);
        level.removeBlock(pos4, false);
    }

    public List<BlockPos> createAoe(Level level) {
        eraseOldAoe(level);

        List<BlockPos> aoe = new ArrayList<>();
        Vec3 center = getBlockLookAt();
        BlockPos centerBlock = new BlockPos((int) center.x, (int) center.y, (int) center.z);

        int centerBlockX = centerBlock.getX();
        int centerBlockZ = centerBlock.getZ();

        BlockPos pos0 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX, centerBlockZ);
        this.X0 = pos0.getX();
        this.Y0 = pos0.getY();
        this.Z0 = pos0.getZ();
        BlockPos pos1 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 1, centerBlockZ);
        this.X1 = pos1.getX();
        this.Y1 = pos1.getY();
        this.Z1 = pos1.getZ();
        BlockPos pos2 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 1, centerBlockZ);
        this.X2 = pos2.getX();
        this.Y2 = pos2.getY();
        this.Z2 = pos2.getZ();
        BlockPos pos3 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX, centerBlockZ + 1);
        this.X3 = pos3.getX();
        this.Y3 = pos3.getY();
        this.Z3 = pos3.getZ();
        BlockPos pos4 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX, centerBlockZ - 1);
        this.X4 = pos4.getX();
        this.Y4 = pos4.getY();
        this.Z4 = pos4.getZ();

        aoe.add(pos0);
        aoe.add(pos1);
        aoe.add(pos2);
        aoe.add(pos3);
        aoe.add(pos4);

        return aoe;
    }

    public void copyFrom(MagicAoECapability source) {
        this.X0 = source.X0;
        this.Y0 = source.Y0;
        this.Z0 = source.Z0;

        this.X1 = source.X1;
        this.Y1 = source.Y1;
        this.Z1 = source.Z1;

        this.X2 = source.X2;
        this.Y2 = source.Y2;
        this.Z2 = source.Z2;

        this.X3 = source.X3;
        this.Y3 = source.Y3;
        this.Z3 = source.Z3;

        this.X4 = source.X4;
        this.Y4 = source.Y4;
        this.Z4 = source.Z4;
        MagicAoECapability.prepareAoe = source.prepareAoe;
        MagicAoECapability.readyToUse = source.readyToUse;

    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("x0", X0);
        compoundTag.putInt("y0", Y0);
        compoundTag.putInt("z0", Z0);

        compoundTag.putInt("x1", X1);
        compoundTag.putInt("y1", Y1);
        compoundTag.putInt("z1", Z1);

        compoundTag.putInt("x2", X2);
        compoundTag.putInt("y2", Y2);
        compoundTag.putInt("z2", Z2);

        compoundTag.putInt("x3", X3);
        compoundTag.putInt("y3", Y3);
        compoundTag.putInt("z3", Z3);

        compoundTag.putInt("x4", X4);
        compoundTag.putInt("y4", Y4);
        compoundTag.putInt("z4", Z4);
        compoundTag.putBoolean("prepareAoe", prepareAoe);
        compoundTag.putBoolean("readyToUse", readyToUse);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        X0 = compoundTag.getInt("x0");
        Y0 = compoundTag.getInt("y0");
        Z0 = compoundTag.getInt("z0");

        X1 = compoundTag.getInt("x1");
        Y1 = compoundTag.getInt("y1");
        Z1 = compoundTag.getInt("z1");

        X2 = compoundTag.getInt("x2");
        Y2 = compoundTag.getInt("y2");
        Z2 = compoundTag.getInt("z2");

        X3 = compoundTag.getInt("x3");
        Y3 = compoundTag.getInt("y3");
        Z3 = compoundTag.getInt("z3");

        X4 = compoundTag.getInt("x4");
        Y4 = compoundTag.getInt("y4");
        Z4 = compoundTag.getInt("z4");
        prepareAoe = compoundTag.getBoolean("prepareAoe");
        readyToUse = compoundTag.getBoolean("readyToUse");
    }

}