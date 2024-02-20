package net.h8sh.playermod.capability.pet;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Pet {
    private static boolean hasPet = false;

    public static void setPet(boolean hasPet) {
        Pet.hasPet = hasPet;
    }

    public static boolean hasPet() {
        return hasPet;
    }

    public void copyFrom(Pet source) {
        this.hasPet = source.hasPet;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("hasPet", hasPet);
    }

    public void loadNBTData(CompoundTag nbt) {
        hasPet = nbt.getBoolean("hasPet");
    }

    private enum Pets {
        CAT(0, "cat"),
        HORSE(1, "horse"),
        AXE(2, "axe"),
        BEAR(3, "axe"),
        SKELETON_SPIDER(4, "skeleton_spider"),
        MECHANICAL_CREATURE(5, "mechanical_creature"),
        WOLF(6, "wolf");

        private final int id;
        private final String name;

        Pets(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}
