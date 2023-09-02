package net.h8sh.playermod.capability.profession.reader;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfessionType {
    @SerializedName("name")
    public String name;

    @SerializedName("skills")
    public List<Skill> skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}
