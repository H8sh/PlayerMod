package net.h8sh.playermod.ability.wizard.aoe;

import com.mojang.datafixers.util.Pair;
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
    private static boolean onCD;
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

    private int X5;
    private int Y5;
    private int Z5;

    private int X6;
    private int Y6;
    private int Z6;

    private int X7;
    private int Y7;
    private int Z7;

    private int X8;
    private int Y8;
    private int Z8;

    private int X9;
    private int Y9;
    private int Z9;

    private int X10;
    private int Y10;
    private int Z10;

    private int X11;
    private int Y11;
    private int Z11;

    private int X12;
    private int Y12;
    private int Z12;

    private int X13;
    private int Y13;
    private int Z13;

    private int X14;
    private int Y14;
    private int Z14;

    private int X15;
    private int Y15;
    private int Z15;

    private int X16;
    private int Y16;
    private int Z16;

    private int X17;
    private int Y17;
    private int Z17;

    private int X18;
    private int Y18;
    private int Z18;

    private int X19;
    private int Y19;
    private int Z19;

    private int X20;
    private int Y20;
    private int Z20;

    public static boolean getPrepareAoe() {
        return MagicAoECapability.prepareAoe;
    }

    public static void setPrepareAoe(boolean prepareAoe) {
        MagicAoECapability.prepareAoe = prepareAoe;
    }

    public static boolean getReadyToUse() {
        return MagicAoECapability.readyToUse;
    }

    public static void setReadyToUse(boolean readyToUse) {
        MagicAoECapability.readyToUse = readyToUse;
    }

    public static Vec3 getBlockLookAt() {
        Player player = Minecraft.getInstance().player;
        HitResult hitResult = player.pick(20, Minecraft.getInstance().getPartialTick(), false);
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

    public static boolean isOnCD() {
        return onCD;
    }

    public static void setOnCD(boolean onCD) {
        MagicAoECapability.onCD = onCD;
    }

    public void spawnAoeMarker(Level level) {
        List<Pair<BlockPos, String>> aoe = createAoe(level);
        for (Pair pair : aoe) {
            BlockPos blockPos = (BlockPos) pair.getFirst();
            level.setBlock(blockPos, ModBlocks.AOE_MARKER_BLOCK.get().defaultBlockState(), 1);
        }

    }

    public void spawnAoe(Level level) {
        int spikeId = 0;
        List<Integer> spikeIds = new ArrayList<>();
        List<Pair<BlockPos, String>> aoe = createAoe(level);
        for (Pair pair : aoe) {
            BlockPos blockPos = (BlockPos) pair.getFirst();
            level.removeBlock(blockPos, false);
            level.setBlock(blockPos, ModBlocks.AOE_BLOCK.get().defaultBlockState(), 1);
            if (pair.getSecond() == "spike") {
                spikeIds.add(spikeId);
            }
            spikeId++;
        }
        for (Integer id : spikeIds) {
            BlockPos spikePos = aoe.get(id).getFirst();
            //TODO
            //level.setBlock(spikePos, ModBlocks.AOE_SPIKE.get().defaultBlockState(), 1);
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
        BlockPos pos5 = new BlockPos(this.X5, this.Y5, this.Z5);
        level.removeBlock(pos5, false);
        BlockPos pos6 = new BlockPos(this.X6, this.Y6, this.Z6);
        level.removeBlock(pos6, false);
        BlockPos pos7 = new BlockPos(this.X7, this.Y7, this.Z7);
        level.removeBlock(pos7, false);
        BlockPos pos8 = new BlockPos(this.X8, this.Y8, this.Z8);
        level.removeBlock(pos8, false);
        BlockPos pos9 = new BlockPos(this.X9, this.Y9, this.Z9);
        level.removeBlock(pos9, false);
        BlockPos pos10 = new BlockPos(this.X10, this.Y10, this.Z10);
        level.removeBlock(pos10, false);
        BlockPos pos11 = new BlockPos(this.X11, this.Y11, this.Z11);
        level.removeBlock(pos11, false);
        BlockPos pos12 = new BlockPos(this.X12, this.Y12, this.Z12);
        level.removeBlock(pos12, false);
        BlockPos pos13 = new BlockPos(this.X13, this.Y13, this.Z13);
        level.removeBlock(pos13, false);
        BlockPos pos14 = new BlockPos(this.X14, this.Y14, this.Z14);
        level.removeBlock(pos14, false);
        BlockPos pos15 = new BlockPos(this.X15, this.Y15, this.Z15);
        level.removeBlock(pos15, false);
        BlockPos pos16 = new BlockPos(this.X16, this.Y16, this.Z16);
        level.removeBlock(pos16, false);
        BlockPos pos17 = new BlockPos(this.X17, this.Y17, this.Z17);
        level.removeBlock(pos17, false);
        BlockPos pos18 = new BlockPos(this.X18, this.Y18, this.Z18);
        level.removeBlock(pos18, false);
        BlockPos pos19 = new BlockPos(this.X19, this.Y19, this.Z19);
        level.removeBlock(pos19, false);
        BlockPos pos20 = new BlockPos(this.X20, this.Y20, this.Z20);
        level.removeBlock(pos20, false);
    }

    public List<Pair<BlockPos, String>> createAoe(Level level) {
        eraseOldAoe(level);

        List<Pair<BlockPos, String>> aoe = new ArrayList<>();
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
        BlockPos pos5 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 1, centerBlockZ + 1);
        this.X5 = pos5.getX();
        this.Y5 = pos5.getY();
        this.Z5 = pos5.getZ();
        BlockPos pos6 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 1, centerBlockZ - 1);
        this.X6 = pos6.getX();
        this.Y6 = pos6.getY();
        this.Z6 = pos6.getZ();
        BlockPos pos7 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 1, centerBlockZ - 1);
        this.X7 = pos7.getX();
        this.Y7 = pos7.getY();
        this.Z7 = pos7.getZ();
        BlockPos pos8 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 1, centerBlockZ + 1);
        this.X8 = pos8.getX();
        this.Y8 = pos8.getY();
        this.Z8 = pos8.getZ();
        BlockPos pos9 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 2, centerBlockZ);
        this.X9 = pos9.getX();
        this.Y9 = pos9.getY();
        this.Z9 = pos9.getZ();
        BlockPos pos10 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 2, centerBlockZ - 1);
        this.X10 = pos10.getX();
        this.Y10 = pos10.getY();
        this.Z10 = pos10.getZ();
        BlockPos pos11 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 2, centerBlockZ + 1);
        this.X11 = pos11.getX();
        this.Y11 = pos11.getY();
        this.Z11 = pos11.getZ();
        BlockPos pos12 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 2, centerBlockZ - 1);
        this.X12 = pos12.getX();
        this.Y12 = pos12.getY();
        this.Z12 = pos12.getZ();
        BlockPos pos13 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 2, centerBlockZ + 1);
        this.X13 = pos13.getX();
        this.Y13 = pos13.getY();
        this.Z13 = pos13.getZ();
        BlockPos pos14 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 2, centerBlockZ);
        this.X14 = pos14.getX();
        this.Y14 = pos14.getY();
        this.Z14 = pos14.getZ();
        BlockPos pos15 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX, centerBlockZ - 2);
        this.X15 = pos15.getX();
        this.Y15 = pos15.getY();
        this.Z15 = pos15.getZ();
        BlockPos pos16 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 1, centerBlockZ - 2);
        this.X16 = pos16.getX();
        this.Y16 = pos16.getY();
        this.Z16 = pos16.getZ();
        BlockPos pos17 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 1, centerBlockZ - 2);
        this.X17 = pos17.getX();
        this.Y17 = pos17.getY();
        this.Z17 = pos17.getZ();
        BlockPos pos18 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX, centerBlockZ + 2);
        this.X18 = pos18.getX();
        this.Y18 = pos18.getY();
        this.Z18 = pos18.getZ();
        BlockPos pos19 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX + 1, centerBlockZ + 2);
        this.X19 = pos19.getX();
        this.Y19 = pos19.getY();
        this.Z19 = pos19.getZ();
        BlockPos pos20 = getTopNonCollidingPos(level, EntityType.WOLF, centerBlockX - 1, centerBlockZ + 2);
        this.X20 = pos20.getX();
        this.Y20 = pos20.getY();
        this.Z20 = pos20.getZ();

        aoe.add(new Pair<>(pos0, ""));
        aoe.add(new Pair<>(pos1, ""));
        aoe.add(new Pair<>(pos2, ""));
        aoe.add(new Pair<>(pos3, ""));
        aoe.add(new Pair<>(pos4, ""));
        aoe.add(new Pair<>(pos5, ""));
        aoe.add(new Pair<>(pos6, ""));
        aoe.add(new Pair<>(pos7, ""));
        aoe.add(new Pair<>(pos8, ""));
        aoe.add(new Pair<>(pos9, ""));
        aoe.add(new Pair<>(pos10, ""));
        aoe.add(new Pair<>(pos11, ""));
        aoe.add(new Pair<>(pos12, ""));
        aoe.add(new Pair<>(pos13, ""));
        aoe.add(new Pair<>(pos14, ""));
        aoe.add(new Pair<>(pos15, ""));
        aoe.add(new Pair<>(pos16, ""));
        aoe.add(new Pair<>(pos17, ""));
        aoe.add(new Pair<>(pos18, ""));
        aoe.add(new Pair<>(pos19, ""));
        aoe.add(new Pair<>(pos20, ""));

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

        this.X5 = source.X5;
        this.Y5 = source.Y5;
        this.Z5 = source.Z5;

        this.X6 = source.X6;
        this.Y6 = source.Y6;
        this.Z6 = source.Z6;

        this.X7 = source.X7;
        this.Y7 = source.Y7;
        this.Z7 = source.Z7;

        this.X8 = source.X8;
        this.Y8 = source.Y8;
        this.Z8 = source.Z8;

        this.X9 = source.X9;
        this.Y9 = source.Y9;
        this.Z9 = source.Z9;

        this.X10 = source.X10;
        this.Y10 = source.Y10;
        this.Z10 = source.Z10;

        this.X11 = source.X11;
        this.Y11 = source.Y11;
        this.Z11 = source.Z11;

        this.X12 = source.X12;
        this.Y12 = source.Y12;
        this.Z12 = source.Z12;

        this.X13 = source.X13;
        this.Y13 = source.Y13;
        this.Z13 = source.Z13;

        this.X14 = source.X14;
        this.Y14 = source.Y14;
        this.Z14 = source.Z14;

        this.X15 = source.X15;
        this.Y15 = source.Y15;
        this.Z15 = source.Z15;

        this.X16 = source.X16;
        this.Y16 = source.Y16;
        this.Z16 = source.Z16;

        this.X17 = source.X17;
        this.Y17 = source.Y17;
        this.Z17 = source.Z17;

        this.X18 = source.X18;
        this.Y18 = source.Y18;
        this.Z18 = source.Z18;

        this.X19 = source.X19;
        this.Y19 = source.Y19;
        this.Z19 = source.Z19;

        this.X20 = source.X20;
        this.Y20 = source.Y20;
        this.Z20 = source.Z20;

        MagicAoECapability.prepareAoe = source.prepareAoe;
        MagicAoECapability.readyToUse = source.readyToUse;
        MagicAoECapability.onCD = source.onCD;

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

        compoundTag.putInt("x5", X5);
        compoundTag.putInt("y5", Y5);
        compoundTag.putInt("z5", Z5);

        compoundTag.putInt("x6", X6);
        compoundTag.putInt("y6", Y6);
        compoundTag.putInt("z6", Z6);

        compoundTag.putInt("x7", X7);
        compoundTag.putInt("y7", Y7);
        compoundTag.putInt("z7", Z7);

        compoundTag.putInt("x8", X8);
        compoundTag.putInt("y8", Y8);
        compoundTag.putInt("z8", Z8);

        compoundTag.putInt("x9", X9);
        compoundTag.putInt("y9", Y9);
        compoundTag.putInt("z9", Z9);

        compoundTag.putInt("x10", X10);
        compoundTag.putInt("y10", Y10);
        compoundTag.putInt("z10", Z10);

        compoundTag.putInt("x11", X11);
        compoundTag.putInt("y11", Y11);
        compoundTag.putInt("z11", Z11);

        compoundTag.putInt("x12", X12);
        compoundTag.putInt("y12", Y12);
        compoundTag.putInt("z12", Z12);

        compoundTag.putInt("x13", X13);
        compoundTag.putInt("y13", Y13);
        compoundTag.putInt("z13", Z13);

        compoundTag.putInt("x14", X14);
        compoundTag.putInt("y14", Y14);
        compoundTag.putInt("z14", Z14);

        compoundTag.putInt("x15", X15);
        compoundTag.putInt("y15", Y15);
        compoundTag.putInt("z15", Z15);

        compoundTag.putInt("x16", X16);
        compoundTag.putInt("y16", Y16);
        compoundTag.putInt("z16", Z16);

        compoundTag.putInt("x17", X17);
        compoundTag.putInt("y17", Y17);
        compoundTag.putInt("z17", Z17);

        compoundTag.putInt("x18", X18);
        compoundTag.putInt("y18", Y18);
        compoundTag.putInt("z18", Z18);

        compoundTag.putInt("x19", X19);
        compoundTag.putInt("y19", Y19);
        compoundTag.putInt("z19", Z19);

        compoundTag.putInt("x20", X20);
        compoundTag.putInt("y20", Y20);
        compoundTag.putInt("z20", Z20);

        compoundTag.putBoolean("prepareAoe", prepareAoe);
        compoundTag.putBoolean("readyToUse", readyToUse);
        compoundTag.putBoolean("onCD", onCD);
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

        X5 = compoundTag.getInt("x5");
        Y5 = compoundTag.getInt("y5");
        Z5 = compoundTag.getInt("z5");

        X6 = compoundTag.getInt("x6");
        Y6 = compoundTag.getInt("y6");
        Z6 = compoundTag.getInt("z6");

        X7 = compoundTag.getInt("x7");
        Y7 = compoundTag.getInt("y7");
        Z7 = compoundTag.getInt("z7");

        X8 = compoundTag.getInt("x8");
        Y8 = compoundTag.getInt("y8");
        Z8 = compoundTag.getInt("z8");

        X9 = compoundTag.getInt("x9");
        Y9 = compoundTag.getInt("y9");
        Z9 = compoundTag.getInt("z9");

        X10 = compoundTag.getInt("x10");
        Y10 = compoundTag.getInt("y10");
        Z10 = compoundTag.getInt("z10");

        X11 = compoundTag.getInt("x11");
        Y11 = compoundTag.getInt("y11");
        Z11 = compoundTag.getInt("z11");

        X12 = compoundTag.getInt("x12");
        Y12 = compoundTag.getInt("y12");
        Z12 = compoundTag.getInt("z12");

        X13 = compoundTag.getInt("x13");
        Y13 = compoundTag.getInt("y13");
        Z13 = compoundTag.getInt("z13");

        X14 = compoundTag.getInt("x14");
        Y14 = compoundTag.getInt("y14");
        Z14 = compoundTag.getInt("z14");

        X15 = compoundTag.getInt("x15");
        Y15 = compoundTag.getInt("y15");
        Z15 = compoundTag.getInt("z15");

        X16 = compoundTag.getInt("x16");
        Y16 = compoundTag.getInt("y16");
        Z16 = compoundTag.getInt("z16");

        X17 = compoundTag.getInt("x17");
        Y17 = compoundTag.getInt("y17");
        Z17 = compoundTag.getInt("z17");

        X18 = compoundTag.getInt("x18");
        Y18 = compoundTag.getInt("y18");
        Z18 = compoundTag.getInt("z18");

        X19 = compoundTag.getInt("x19");
        Y19 = compoundTag.getInt("y19");
        Z19 = compoundTag.getInt("z19");

        X20 = compoundTag.getInt("x20");
        Y20 = compoundTag.getInt("y20");
        Z20 = compoundTag.getInt("z20");

        prepareAoe = compoundTag.getBoolean("prepareAoe");
        readyToUse = compoundTag.getBoolean("readyToUse");
        onCD = compoundTag.getBoolean("onCD");
    }

}