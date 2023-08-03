package net.h8sh.playermod.capability.riding;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Riding {
    private static Ridings riding;
    private int ridingKnown;
    private ResourceLocation RIDING_TEXTURE;

    public static ResourceLocation getRidingTexture(int ridingId) {
        return new ResourceLocation(PlayerMod.MODID, "textures/riding/" + ridingId + "_texture.png");
    }

    public static String getRidingByName() {
        return riding.getName();
    }

    public static Ridings getRiding() {
        return riding;
    }

    public int getRidingKnown() {
        return ridingKnown;
    }

    public void addRiding(Ridings ridings) {
        this.RIDING_TEXTURE = getRidingTexture(ridings.getId());
        this.riding = ridings;
        this.ridingKnown += 1;
    }

    public void resetRiding() {
        this.riding = Ridings.NO_MOUNT;
        this.ridingKnown = 0;
        this.RIDING_TEXTURE = getRidingTexture(Ridings.NO_MOUNT.getId());
    }

    public void copyFrom(Riding source) {
        this.riding = source.riding;
        this.ridingKnown = source.ridingKnown;
        this.RIDING_TEXTURE = source.RIDING_TEXTURE;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("riding", riding.id);
        nbt.putInt("ridingKnown", ridingKnown);
    }

    public void loadNBTData(CompoundTag nbt) {
        riding = Ridings.getRidingFromId(nbt.getInt("riding"));
        ridingKnown = nbt.getInt("ridingKnown");
    }

    public enum Ridings {
        NO_MOUNT(0, "no_mount"),
        PALADIN_MOUNT(1, "paladin_mount"),
        WIZARD_MOUNT(2, "wizard_mount"),
        DRUID_MOUNT(3, "druid_mount");

        private final int id;
        private final String name;

        Ridings(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Ridings getRidingFromId(int id) {
            switch (id) {
                case 1:
                    return Ridings.PALADIN_MOUNT;
                case 2:
                    return Ridings.WIZARD_MOUNT;
                case 3:
                    return Ridings.DRUID_MOUNT;
                default:
                    return Ridings.NO_MOUNT;
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
