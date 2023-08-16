package net.h8sh.playermod.hero;

public enum CustomEquipmentSlot {
    MAINHAND(CustomEquipmentSlot.Type.HAND, 0, 0, "mainhand"),
    OFFHAND(CustomEquipmentSlot.Type.HAND, 1, 5, "offhand"),
    FEET(CustomEquipmentSlot.Type.ARMOR, 0, 1, "feet"),
    LEGS(CustomEquipmentSlot.Type.ARMOR, 1, 2, "legs"),
    CHEST(CustomEquipmentSlot.Type.ARMOR, 2, 3, "chest"),
    HEAD(CustomEquipmentSlot.Type.ARMOR, 3, 4, "head");

    private final Type type;
    private final int index;
    private final int filterFlag;
    private final String name;

    private CustomEquipmentSlot(Type pType, int pIndex, int pFilterFlag, String pName) {
        this.type = pType;
        this.index = pIndex;
        this.filterFlag = pFilterFlag;
        this.name = pName;
    }

    public Type getType() {
        return this.type;
    }

    public int getIndex() {
        return this.index;
    }

    public int getIndex(int pBaseIndex) {
        return pBaseIndex + this.index;
    }

    /**
     * Gets the actual slot index.
     */
    public int getFilterFlag() {
        return this.filterFlag;
    }

    public String getName() {
        return this.name;
    }

    public boolean isArmor() {
        return this.type == Type.ARMOR;
    }

    public static CustomEquipmentSlot byName(String pTargetName) {
        for(CustomEquipmentSlot equipmentslot : values()) {
            if (equipmentslot.getName().equals(pTargetName)) {
                return equipmentslot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + pTargetName + "'");
    }

    /**
     * Returns the slot type based on the slot group and the index inside that group.
     */
    public static CustomEquipmentSlot byTypeAndIndex(Type pSlotType, int pSlotIndex) {
        for(CustomEquipmentSlot equipmentslot : values()) {
            if (equipmentslot.getType() == pSlotType && equipmentslot.getIndex() == pSlotIndex) {
                return equipmentslot;
            }
        }

        throw new IllegalArgumentException("Invalid slot '" + pSlotType + "': " + pSlotIndex);
    }

    public static enum Type {
        HAND,
        ARMOR;
    }
}