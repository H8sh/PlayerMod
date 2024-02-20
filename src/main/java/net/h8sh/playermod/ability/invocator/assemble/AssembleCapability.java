package net.h8sh.playermod.ability.invocator.assemble;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class AssembleCapability {

    private static boolean isAssembling;
    private static int getEvolution(Minions minions) {
        if (minions.getId() == 4) {
            return Minions.DADDY_SKELETON.getId();
        } else {
            return minions.getId() + 1;
        }
    }

    private static int getReverseEvolution(Minions minions) {
        if (minions.getId() == 0) {
            return Minions.BABY_SKELETON.getId();
        } else {
            return minions.getId() - 1;
        }
    }

    public static boolean getIsAssembling() {
        return AssembleCapability.isAssembling;
    }

    public static void setIsAssembling(boolean isAssembling) {
        AssembleCapability.isAssembling = isAssembling;
    }

    public void copyFrom(AssembleCapability source) {
        AssembleCapability.isAssembling = source.isAssembling;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("isAssembling", isAssembling);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        isAssembling = compoundTag.getBoolean("isAssembling");
    }

    private enum Minions {
        BABY_SKELETON(0, "baby_skeleton"),
        JUNIOR_SKELETON(1, "junior_skeleton"),
        ELDER_SKELETON(2, "elder_skeleton"),
        DADDY_SKELETON(3, "daddy_skeleton");

        private final int id;
        private final String name;

        Minions(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Minions getMinionsFromId(int id) {
            switch (id) {
                case 1:
                    return BABY_SKELETON;
                case 2:
                    return JUNIOR_SKELETON;
                case 3:
                    return ELDER_SKELETON;
                default:
                    return DADDY_SKELETON;
            }
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

}