package net.h8sh.playermod.skill.reader;

import com.google.gson.annotations.SerializedName;

public class Types {

    @SerializedName("skill_type")
    public SkillType skillType;

    public SkillType getProfessionType() {
        return skillType;
    }

    public void setProfessionType(SkillType skillType) {
        this.skillType = skillType;
    }

}
