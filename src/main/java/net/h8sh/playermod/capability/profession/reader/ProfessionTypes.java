package net.h8sh.playermod.capability.profession.reader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfessionTypes {

    private static ProfessionTypes PROFESSION_TYPE;

    @SerializedName("profession_types")
    public List<Types> types;

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public static void setPrototypesFromJson(ProfessionTypes professionsType) {
        ProfessionTypes.PROFESSION_TYPE = professionsType;
    }

    public static ProfessionTypes getProfessionType() {
        return PROFESSION_TYPE;
    }
}
