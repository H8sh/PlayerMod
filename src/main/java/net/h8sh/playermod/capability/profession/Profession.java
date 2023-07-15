package net.h8sh.playermod.capability.profession;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Profession {
    private static int profession;
    private int professionKnown;

    private ResourceLocation PROFESSION_TEXTURE;

    public static ResourceLocation getProfessionTexture(int professionId) {
        return new ResourceLocation(PlayerMod.MODID, "textures/profession/" + professionId + "_texture.png");
    }

    public static String getProfessionByName() {
        return Professions.getNameFormId(profession);
    }

    public static int getProfession() {
        return profession;
    }



    public int getProfessionKnown() {
        return professionKnown;
    }

    public void addProfession(Professions professions) {
        this.PROFESSION_TEXTURE = getProfessionTexture(professions.getId());
        this.profession = professions.getId();
        this.professionKnown += 1;
    }

    public static void setProfession(int newProfession){
        profession = newProfession;
    }

    public void resetProfession() {
        this.profession = Professions.BASIC.getId();
        this.professionKnown = 0;
        this.PROFESSION_TEXTURE = getProfessionTexture(Professions.BASIC.getId());
    }

    public void copyFrom(Profession source) {
        this.profession = source.profession;
        this.professionKnown = source.professionKnown;
        this.PROFESSION_TEXTURE = source.PROFESSION_TEXTURE;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("profession", profession);
        nbt.putInt("professionKnown", professionKnown);
    }

    public void loadNBTData(CompoundTag nbt) {
        profession = nbt.getInt("profession");
        professionKnown = nbt.getInt("professionKnown");
    }

    public enum Professions {
        BASIC(0, "Basic"),
        PALADIN(1, "Paladin"),
        WIZARD(2, "Wizard"),
        DRUID(3, "Druid");

        private final int id;
        private final String name;

        Professions(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public static String getNameFormId(int id) {
            switch (id) {
                case 1:
                    return Professions.PALADIN.getName();
                case 2:
                    return Professions.WIZARD.getName();
                case 3:
                    return Professions.DRUID.getName();
                default:
                    return Professions.BASIC.getName();
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
