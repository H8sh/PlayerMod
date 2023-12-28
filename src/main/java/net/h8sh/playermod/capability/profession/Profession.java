package net.h8sh.playermod.capability.profession;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
@AutoRegisterCapability
public class Profession {
    private static Professions profession;
    private int professionKnown;
    private ResourceLocation PROFESSION_TEXTURE;

    public static ResourceLocation getProfessionTexture(int professionId) {
        return new ResourceLocation(PlayerMod.MODID, "textures/profession/" + professionId + "_texture.png");
    }

    public static ResourceLocation getAbilityTexture(String abilityId) {
        return new ResourceLocation(PlayerMod.MODID, "textures/ability/" + abilityId + ".png");
    }

    public static String getProfessionName() {
        return profession.getName();
    }

    public static Professions getProfession() {
        return profession;
    }

    public int getProfessionKnown() {
        return professionKnown;
    }

    public void addProfession(Professions professions) {
        this.PROFESSION_TEXTURE = getProfessionTexture(professions.getId());
        this.profession = professions;
        this.professionKnown += 1;
    }

    public void resetProfession() {
        this.profession = Professions.BASIC;
        this.professionKnown = 0;
        this.PROFESSION_TEXTURE = getProfessionTexture(Professions.BASIC.getId());
    }

    public void copyFrom(Profession source) {
        this.profession = source.profession;
        this.professionKnown = source.professionKnown;
        this.PROFESSION_TEXTURE = source.PROFESSION_TEXTURE;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("profession", profession.getId());
        nbt.putInt("professionKnown", professionKnown);
    }

    public void loadNBTData(CompoundTag nbt) {
        profession = Professions.getProfessionFromId(nbt.getInt("profession"));
        professionKnown = nbt.getInt("professionKnown");
    }

    public enum Professions {
        BASIC(0, "steve"),
        PALADIN(1, "paladin"),
        WIZARD(2, "wizard"),
        DRUID(3, "druid"),
        FIREMETA(4, "fireMeta"),
        AQUAMETA(5, "aquaMeta"),
        WINDMETA(6, "windMeta"),
        SPIRITUSMETA(7, "spiritusMeta"),
        ROGUE(8, "rogue"),
        BERSERK(9, "berserk"),
        INVOCATOR(10, "invocator");

        private final int id;
        private final String name;

        Professions(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static Professions getProfessionFromId(int id) {
            return switch (id) {
                case 1 -> Professions.PALADIN;
                case 2 -> Professions.WIZARD;
                case 3 -> Professions.DRUID;
                case 4 -> Professions.FIREMETA;
                case 5 -> Professions.AQUAMETA;
                case 6 -> Professions.WINDMETA;
                case 7 -> Professions.SPIRITUSMETA;
                case 8 -> Professions.ROGUE;
                case 9 -> Professions.BERSERK;
                case 10 -> Professions.INVOCATOR;
                default -> Professions.BASIC;
            };
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

    }


}
