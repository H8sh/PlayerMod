package net.h8sh.playermod.capability.profession.reader;

import com.google.gson.annotations.SerializedName;

public class Skill {

    @SerializedName("first_spell")
    public String firstSpell;

    @SerializedName("second_spell")
    public String secondSpell;

    @SerializedName("third_spell")
    public String thirdSpell;

    @SerializedName("ultimate_spell")
    public String ultimate;

    public String getFirstSpell() {
        return firstSpell;
    }

    public String getSecondSpell() {
        return secondSpell;
    }

    public String getThirdSpell() {
        return thirdSpell;
    }

    public String getUltimate() {
        return ultimate;
    }
}
