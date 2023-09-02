package net.h8sh.playermod.capability.profession.reader;

import com.google.gson.annotations.SerializedName;

public class Types {

    @SerializedName("profession_type")
    public ProfessionType professionType;

    public ProfessionType getProfessionType() {
        return professionType;
    }

    public void setProfessionType(ProfessionType professionType) {
        this.professionType = professionType;
    }

}
