package net.h8sh.playermod.capability.ability.druid.metamorphose;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Metamorphose {
    private static Metamorphoses metamorphose;
    private int metamorphoseKnown;
    private ResourceLocation METAMORPHOSE_TEXTURE;

    public static ResourceLocation getMetamorphoseTexture(int metamorphoseId) {
        return new ResourceLocation(PlayerMod.MODID, "textures/metamorphose/" + metamorphoseId + "_texture.png");
    }

    public static String getMetamorphoseByName() {
        return metamorphose.getName();
    }

    public static Metamorphoses getMetamorphose() {
        return metamorphose;
    }

    public int getMetamorphoseKnown() {
        return metamorphoseKnown;
    }

    public void addMetamorphose(Metamorphoses metamorphoses) {
        this.METAMORPHOSE_TEXTURE = getMetamorphoseTexture(metamorphoses.getId());
        this.metamorphose = metamorphoses;
        this.metamorphoseKnown += 1;
    }

    public void resetMetamorphose() {
        this.metamorphose = Metamorphoses.NO_METAMORPHOSE;
        this.metamorphoseKnown = 0;
        this.METAMORPHOSE_TEXTURE = getMetamorphoseTexture(Metamorphoses.NO_METAMORPHOSE.getId());
    }

    public void copyFrom(Metamorphose source) {
        this.metamorphose = source.metamorphose;
        this.metamorphoseKnown = source.metamorphoseKnown;
        this.METAMORPHOSE_TEXTURE = source.METAMORPHOSE_TEXTURE;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("metamorphose", metamorphose.id);
        nbt.putInt("metamorphoseKnown", metamorphoseKnown);
    }

    public void loadNBTData(CompoundTag nbt) {
        metamorphose = Metamorphoses.getMetamorphoseFromId(nbt.getInt("metamorphose"));
        metamorphoseKnown = nbt.getInt("metamorphoseKnown");
    }

    public enum Metamorphoses {
        NO_METAMORPHOSE(0, "no_metamorphose"),
        AQUA(1, "aqua"),
        FIRE(2, "fire"),
        WIND(3, "wind"),
        SPIRITUS(4, "spiritus");

        private final int id;
        private final String name;

        Metamorphoses(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Metamorphoses getMetamorphoseFromId(int id) {
            switch (id) {
                case 1:
                    return Metamorphoses.AQUA;
                case 2:
                    return Metamorphoses.FIRE;
                case 3:
                    return Metamorphoses.WIND;
                case 4:
                    return Metamorphoses.SPIRITUS;
                default:
                    return Metamorphoses.NO_METAMORPHOSE;
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
