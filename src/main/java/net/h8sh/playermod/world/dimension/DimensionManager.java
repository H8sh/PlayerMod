package net.h8sh.playermod.world.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class DimensionManager {
    private static Direction direction;
    private static int x;
    private static int y;

    private static int getX() {
        return x;
    }

    private static void setX(int x) {
        DimensionManager.x = x;
    }

    private static int getY() {
        return y;
    }

    private static void setY(int y) {
        DimensionManager.y = y;
    }

    private static Direction getDirection() {
        return direction;
    }

    public static void setDirection(Direction direction) {
        DimensionManager.direction = direction;
    }

    private static int getStartingPoint(String[][] map) {
        return map.length / 2 + 1;
    }

    public static String getStartingZone(String[][] map) {
        setX(getStartingPoint(map));
        setY(getStartingPoint(map));
        return map[getStartingPoint(map)][getStartingPoint(map)];
    }

    public static String getToNextZone(String[][] map) {
        switch (getDirection()) {
            case WEST:
                setX(getX() - 1);
                return map[getX()][getY()];
            case SOUTH:
                setY(getY() + 1);
                return map[getX()][getY()];
            case NORTH:
                setY(getY() - 1);
                return map[getX()][getY()];
            case EAST:
                setX(getX() + 1);
                return map[getX()][getY()];
            default:
                return getStartingZone(map);
        }
    }

    public static ResourceKey<Level> getResourceKey(String dimensionName) {
        switch (dimensionName) {
            case " ":
                return ModDimensions.WONDERLANDS_LEVEL_KEY;
            default:
                return Level.OVERWORLD;
        }
    }

    public void copyFrom(DimensionManager source) {
        DimensionManager.x = source.x;
        DimensionManager.y = source.y;
        DimensionManager.direction = source.direction;

    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putInt("x", x);
        compoundTag.putInt("y", y);
        compoundTag.putString("direction", direction.getName());
    }

    public void loadNBTData(CompoundTag compoundTag) {
        x = compoundTag.getInt("x");
        y = compoundTag.getInt("y");
        direction = Direction.byName(compoundTag.getString("direction"));
    }
}
