package net.h8sh.playermod.ability.druid.firemeta.damage_spell;

import net.h8sh.playermod.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;

@AutoRegisterCapability
public class DamageSpellCapability {

    private static final int LENGTH = 5;
    private static final int WEIGH = 5;
    private static boolean onDamageSpell;

    public static boolean getOnDamageSpell() {
        return DamageSpellCapability.onDamageSpell;
    }

    public static void setOnDamageSpell(boolean onDamageSpell) {
        DamageSpellCapability.onDamageSpell = onDamageSpell;
    }

    private static List<BlockPos> northZone(Player player) {
        List<BlockPos> northZone = new ArrayList<>();
        for (int length = 0; length < LENGTH; length++) {
            for (int weigh = 0; weigh < WEIGH; weigh++) {
                BlockPos blockPos = new BlockPos(player.blockPosition().getX() - weigh + WEIGH / 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2 - length);
                northZone.add(blockPos);
            }
        }
        BlockPos northZoneLeftDownCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);
        BlockPos northZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 6);
        BlockPos northZoneRightDownCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);
        BlockPos northZoneRightUpCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 6);

        northZone.remove(northZoneRightUpCorner);
        northZone.remove(northZoneLeftDownCorner);
        northZone.remove(northZoneLeftUpCorner);
        northZone.remove(northZoneRightDownCorner);

        return northZone;
    }

    private static List<BlockPos> southZone(Player player) {
        List<BlockPos> southZone = new ArrayList<>();
        for (int length = 0; length < LENGTH; length++) {
            for (int weigh = 0; weigh < WEIGH; weigh++) {
                BlockPos blockPos = new BlockPos(player.blockPosition().getX() - weigh + WEIGH / 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2 + length);
                southZone.add(blockPos);
            }
        }
        BlockPos southZoneLeftDownCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);
        BlockPos southZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 6);
        BlockPos southZoneRightDownCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);
        BlockPos southZoneRightUpCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 6);

        southZone.remove(southZoneRightUpCorner);
        southZone.remove(southZoneLeftDownCorner);
        southZone.remove(southZoneLeftUpCorner);
        southZone.remove(southZoneRightDownCorner);

        return southZone;
    }

    private static List<BlockPos> eastZone(Player player) {
        List<BlockPos> eastZone = new ArrayList<>();
        for (int length = 0; length < LENGTH; length++) {
            for (int weigh = 0; weigh < WEIGH; weigh++) {
                BlockPos blockPos = new BlockPos(player.blockPosition().getX() + 2 + length, player.blockPosition().getY() - 1, player.blockPosition().getZ() - weigh + WEIGH / 2);
                eastZone.add(blockPos);
            }
        }
        BlockPos eastZoneLeftDownCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);
        BlockPos eastZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() + 6, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);
        BlockPos eastZoneRightDownCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);
        BlockPos eastZoneRightUpCorner = new BlockPos(player.blockPosition().getX() + 6, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);

        eastZone.remove(eastZoneRightUpCorner);
        eastZone.remove(eastZoneLeftDownCorner);
        eastZone.remove(eastZoneLeftUpCorner);
        eastZone.remove(eastZoneRightDownCorner);

        return eastZone;
    }

    private static List<BlockPos> westZone(Player player) {
        List<BlockPos> westZone = new ArrayList<>();
        for (int length = 0; length < LENGTH; length++) {
            for (int weigh = 0; weigh < WEIGH; weigh++) {
                BlockPos blockPos = new BlockPos(player.blockPosition().getX() - 2 - length, player.blockPosition().getY() - 1, player.blockPosition().getZ() - weigh + WEIGH / 2);
                westZone.add(blockPos);
            }
        }
        BlockPos westZoneLeftDownCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);
        BlockPos westZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() - 6, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);
        BlockPos westZoneRightDownCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);
        BlockPos westZoneRightUpCorner = new BlockPos(player.blockPosition().getX() - 6, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);

        westZone.remove(westZoneRightUpCorner);
        westZone.remove(westZoneLeftDownCorner);
        westZone.remove(westZoneLeftUpCorner);
        westZone.remove(westZoneRightDownCorner);

        return westZone;
    }

    private static List<BlockPos> giveCorrectAABBPoints(Player player) {
        List<BlockPos> correctPoints = new ArrayList<>();
        Direction direction = Direction.fromYRot(player.getYRot());
        switch (direction) {
            case NORTH -> {
                BlockPos northZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 6);
                BlockPos northZoneRightDownCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() + 3, player.blockPosition().getZ() - 2);
                correctPoints.add(new BlockPos(northZoneLeftUpCorner));
                correctPoints.add(new BlockPos(northZoneRightDownCorner));
            }
            case SOUTH -> {
                BlockPos southZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 6);
                BlockPos southZoneRightDownCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() + 3, player.blockPosition().getZ() + 2);
                correctPoints.add(new BlockPos(southZoneLeftUpCorner));
                correctPoints.add(new BlockPos(southZoneRightDownCorner));
            }
            case EAST -> {
                BlockPos eastZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() + 6, player.blockPosition().getY() - 1, player.blockPosition().getZ() - 2);
                BlockPos eastZoneRightDownCorner = new BlockPos(player.blockPosition().getX() + 2, player.blockPosition().getY() + 3, player.blockPosition().getZ() + 2);
                correctPoints.add(new BlockPos(eastZoneLeftUpCorner));
                correctPoints.add(new BlockPos(eastZoneRightDownCorner));
            }
            case WEST -> {
                BlockPos westZoneLeftUpCorner = new BlockPos(player.blockPosition().getX() - 6, player.blockPosition().getY() - 1, player.blockPosition().getZ() + 2);
                BlockPos westZoneRightDownCorner = new BlockPos(player.blockPosition().getX() - 2, player.blockPosition().getY() + 3, player.blockPosition().getZ() - 2);
                correctPoints.add(new BlockPos(westZoneLeftUpCorner));
                correctPoints.add(new BlockPos(westZoneRightDownCorner));
            }
        }
        return correctPoints;
    }

    private static List<BlockPos> selectCorrectZone(Player player) {
        List<BlockPos> correctZone = new ArrayList<>();
        Direction direction = Direction.fromYRot(player.getYRot());
        switch (direction) {
            case NORTH -> {
                correctZone = northZone(player);
            }
            case SOUTH -> {
                correctZone = southZone(player);
            }
            case EAST -> {
                correctZone = eastZone(player);
            }
            case WEST -> {
                correctZone = westZone(player);
            }
        }
        return correctZone;
    }

    public static void selectEntitiesToBump(Level level, Player player) {
        List<LivingEntity> correctEntities = new ArrayList<>();
        List<BlockPos> correctAABBZonePoints = giveCorrectAABBPoints(player);
        List<Entity> entities = level.getEntities(player, new AABB(correctAABBZonePoints.get(0), correctAABBZonePoints.get(1)), EntitySelector.ENTITY_STILL_ALIVE);
        List<BlockPos> zone = selectCorrectZone(player);

        for (Entity entity : entities) {
            if (!(entity instanceof Player) && entity instanceof LivingEntity) {
                int entityX = entity.blockPosition().getX();
                int entityY = entity.blockPosition().getY();
                int entityZ = entity.blockPosition().getZ();
                for (BlockPos zoneBlockPos : zone) {
                    int zoneX = zoneBlockPos.getX();
                    int zoneY = zoneBlockPos.getY();
                    int zoneZ = zoneBlockPos.getZ();
                    boolean isHeightEnough = entityY > zoneY - 2 && entityY < zoneY + 4;
                    if (zoneX == entityX && zoneZ == entityZ && isHeightEnough) {
                        correctEntities.add((LivingEntity) entity);
                    }
                }
            }
        }
        createFire(level, zone);
        bump(correctEntities);
    }

    private static void createFire(Level level, List<BlockPos> toBeFired) {
        for (BlockPos blockPos : toBeFired) {
            level.removeBlock(blockPos, false);
            level.setBlock(blockPos, ModBlocks.DAMAGE_SPELL_BLOCK.get().defaultBlockState(), 2);
        }
    }

    private static void bump(List<LivingEntity> entities) {
        for (LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 6, 20, false, true, false));
        }
    }

    private void stopFire(Level level, List<BlockPos> toBeFired) {
        for (BlockPos blockPos : toBeFired) {
            level.removeBlock(blockPos, false);
            level.setBlock(blockPos, Blocks.OBSIDIAN.defaultBlockState(), 1);
        }
    }

    public void copyFrom(DamageSpellCapability source) {
        DamageSpellCapability.onDamageSpell = source.onDamageSpell;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onDamageSpell", onDamageSpell);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onDamageSpell = compoundTag.getBoolean("onDamageSpell");
    }

}