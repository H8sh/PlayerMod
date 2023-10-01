package net.h8sh.playermod.ability.berserk.slam;

import net.h8sh.playermod.ability.berserk.rage.RageCapability;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Random;

@AutoRegisterCapability
public class SlamCapability {
    private static final int HEIGHT = 3;
    private static final int WEIGHT = 3;
    private static final int LENGTH = 5;
    private static final float FIRE_PROBA = 0.2F;

    private static final float SPIKE_PROBA = 0.3F;
    private static final float BLOCK_BRAKE_PROBA = 0.4F;

    private static final Random random = new Random();
    private static boolean onSlam;

    public static boolean getOnSlam() {
        return SlamCapability.onSlam;
    }

    public static void setOnSlam(boolean onSlam) {
        SlamCapability.onSlam = onSlam;
    }

    public static void removeBlock(Player player) {
        Direction direction = Direction.fromYRot(player.getYRot());
        switch (direction) {
            case NORTH -> {
                for (int height = 0; height < HEIGHT; height++) {
                    for (int weight = 0; weight < WEIGHT; weight++) {
                        for (int length = 0; length < LENGTH; length++) {
                            BlockPos blockPos = new BlockPos(player.blockPosition().getX() + weight - WEIGHT / 2, player.blockPosition().getY() + height, player.blockPosition().getZ() - length);

                            singleProba(player, blockPos);
                        }
                    }
                }
                for (int weight = 0; weight < WEIGHT; weight++) {
                    for (int length = 0; length < LENGTH; length++) {
                        BlockPos blockPos = new BlockPos(player.blockPosition().getX() + weight - WEIGHT / 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - length);

                        singleProba(player, blockPos);

                        float q = random.nextFloat(0, 1);
                        if (q < FIRE_PROBA + RageCapability.getSlamFireUp()) {
                            if (player.blockPosition().getX() + weight - WEIGHT / 2 != player.blockPosition().getX()) {
                                BlockState blockstate = BaseFireBlock.getState(player.level(), blockPos);
                                player.level().setBlock(blockPos, blockstate, 11);
                            }
                        }
                    }
                }
                for (int length = 0; length < LENGTH; length++) {
                    BlockPos blockPos = new BlockPos(player.blockPosition().getX(), player.blockPosition().getY() - 2, player.blockPosition().getZ() - length);

                    doubleProba(player, blockPos);
                }
            }
            case EAST -> {
                for (int height = 0; height < HEIGHT; height++) {
                    for (int weight = 0; weight < WEIGHT; weight++) {
                        for (int length = 0; length < LENGTH; length++) {
                            BlockPos blockPos = new BlockPos(player.blockPosition().getX() + length, player.blockPosition().getY() + height, player.blockPosition().getZ() + weight - WEIGHT / 2);

                            singleProba(player, blockPos);
                        }
                    }
                }
                for (int weight = 0; weight < WEIGHT; weight++) {
                    for (int length = 0; length < LENGTH; length++) {
                        BlockPos blockPos = new BlockPos(player.blockPosition().getX() + length, player.blockPosition().getY() - 1, player.blockPosition().getZ() + weight - WEIGHT / 2);

                        singleProba(player, blockPos);

                        float q = random.nextFloat(0, 1);
                        if (q < FIRE_PROBA + RageCapability.getSlamFireUp()) {
                            if (player.blockPosition().getZ() + weight - WEIGHT / 2 != player.blockPosition().getZ()) {
                                BlockState blockstate = BaseFireBlock.getState(player.level(), blockPos);
                                player.level().setBlock(blockPos, blockstate, 11);
                            }
                        }

                    }
                }
                for (int length = 0; length < LENGTH; length++) {
                    BlockPos blockPos = new BlockPos(player.blockPosition().getX() + length, player.blockPosition().getY() - 2, player.blockPosition().getZ());

                    doubleProba(player, blockPos);
                }
            }
            case WEST -> {
                for (int height = 0; height < HEIGHT; height++) {
                    for (int weight = 0; weight < WEIGHT; weight++) {
                        for (int length = 0; length < LENGTH; length++) {
                            BlockPos blockPos = new BlockPos(player.blockPosition().getX() - length, player.blockPosition().getY() + height, player.blockPosition().getZ() + weight - WEIGHT / 2);

                            singleProba(player, blockPos);
                        }
                    }
                }
                for (int weight = 0; weight < WEIGHT; weight++) {
                    for (int length = 0; length < LENGTH; length++) {
                        BlockPos blockPos = new BlockPos(player.blockPosition().getX() - length, player.blockPosition().getY() - 1, player.blockPosition().getZ() + weight - WEIGHT / 2);

                        singleProba(player, blockPos);

                        float q = random.nextFloat(0, 1);
                        if (q < FIRE_PROBA + RageCapability.getSlamFireUp()) {
                            if (player.blockPosition().getZ() + weight - WEIGHT / 2 != player.blockPosition().getZ()) {
                                BlockState blockstate = BaseFireBlock.getState(player.level(), blockPos);
                                player.level().setBlock(blockPos, blockstate, 11);
                            }
                        }
                    }
                }
                for (int length = 0; length < LENGTH; length++) {
                    BlockPos blockPos = new BlockPos(player.blockPosition().getX() - length, player.blockPosition().getY() - 2, player.blockPosition().getZ());

                    doubleProba(player, blockPos);
                }
            }
            case SOUTH -> {
                for (int height = 0; height < HEIGHT; height++) {
                    for (int weight = 0; weight < WEIGHT; weight++) {
                        for (int length = 0; length < LENGTH; length++) {
                            BlockPos blockPos = new BlockPos(player.blockPosition().getX() + weight - WEIGHT / 2, player.blockPosition().getY() + height, player.blockPosition().getZ() + length);

                            singleProba(player, blockPos);
                        }
                    }
                }
                for (int weight = 0; weight < WEIGHT; weight++) {
                    for (int length = 0; length < LENGTH; length++) {
                        BlockPos blockPos = new BlockPos(player.blockPosition().getX() + weight - WEIGHT / 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + length);

                        singleProba(player, blockPos);

                        float q = random.nextFloat(0, 1);
                        if (q < FIRE_PROBA + RageCapability.getSlamFireUp()) {
                            if (player.blockPosition().getX() + weight - WEIGHT / 2 != player.blockPosition().getX()) {
                                BlockState blockstate = BaseFireBlock.getState(player.level(), blockPos);
                                player.level().setBlock(blockPos, blockstate, 11);
                            }
                        }
                    }
                }
                for (int length = 0; length < LENGTH; length++) {
                    BlockPos blockPos = new BlockPos(player.blockPosition().getX(), player.blockPosition().getY() - 2, player.blockPosition().getZ() + length);

                    doubleProba(player, blockPos);
                }
            }
        }
    }

    private static void doubleProba(Player player, BlockPos blockPos) {
        float p = random.nextFloat(0, 1);
        if (p < BLOCK_BRAKE_PROBA + RageCapability.getSlamDestructionUp()) {
            player.level().removeBlock(blockPos, false);
        }

        float q = random.nextFloat(0, 1);
        if (q < FIRE_PROBA + RageCapability.getSlamFireUp()) {
            BlockState blockstate = BaseFireBlock.getState(player.level(), blockPos);
            player.level().setBlock(blockPos, blockstate, 11);
        }
    }

    private static void singleProba(Player player, BlockPos blockPos) {
        float p = random.nextFloat(0, 1);
        if (p < BLOCK_BRAKE_PROBA + RageCapability.getSlamDestructionUp()) {
            player.level().removeBlock(blockPos, false);
        }
    }

    //TODO: add this to the slam effect
    private static void spikeProba(Player player, BlockPos blockPos) {
        float r = random.nextFloat(0, 1);
        if (r < SPIKE_PROBA + RageCapability.getSpikeUp()) {
            //TODO: change to custom spike entity
            CrystalEntity crystal = ModEntities.CRYSTAL.get().create(player.level());
            player.level().addFreshEntity(crystal);
            crystal.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        }
    }

    public void copyFrom(SlamCapability source) {
        SlamCapability.onSlam = source.onSlam;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onSlam", onSlam);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onSlam = compoundTag.getBoolean("onSlam");
    }

}