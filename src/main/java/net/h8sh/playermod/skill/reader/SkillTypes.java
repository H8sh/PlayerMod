package net.h8sh.playermod.skill.reader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SkillTypes {

    private static SkillTypes PROFESSION_TYPE;

    @SerializedName("skill_types")
    public List<Types> types;

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public static void setPrototypesFromJson(SkillTypes professionsType) {
        SkillTypes.PROFESSION_TYPE = professionsType;
    }

    public static SkillTypes getProfessionType() {
        return PROFESSION_TYPE;
    }
}
