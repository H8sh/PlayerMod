package net.h8sh.playermod.capability.narrator;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Narrator {
    private String narrator;

    private boolean JUST_SPAWN_NARRATOR_FLAG;
    private boolean GET_WOOD_NARRATOR_FLAG;
    private boolean GET_COAL_NARRATOR_FLAG;
    private boolean GET_IRON_NARRATOR_FLAG;
    private boolean GET_DIAMOND_NARRATOR_FLAG;
    private boolean GET_NETHER_NARRATOR_FLAG;
    private boolean GET_ENDER_PEARL_NARRATOR_FLAG;
    private boolean GET_EYE_OF_ENDER_NARRATOR_FLAG;
    private boolean GET_END_NARRATOR_FLAG;

    public void copyFrom(Narrator source) {
        this.narrator = source.narrator;

    }

    public void setNarrator(String narrator) {
        this.narrator = narrator;
    }

    public void setFlagSpawn() {
        this.JUST_SPAWN_NARRATOR_FLAG = true;
    }

    public void setFlagWood() {
        this.GET_WOOD_NARRATOR_FLAG = true;
    }

    public void setFlagCoal() {
        this.GET_WOOD_NARRATOR_FLAG = true;
    }
    public void setFlagDiamond() {
        this.GET_DIAMOND_NARRATOR_FLAG = true;
    }

    public void setFlagNether() {
        this.GET_NETHER_NARRATOR_FLAG = true;
    }

    public void setFlagIron() {
        this.GET_IRON_NARRATOR_FLAG = true;
    }
    public void setFlagEyeOfEnder() {
        this.GET_IRON_NARRATOR_FLAG = true;
    }
    public void setFlagEnderPearl() {
        this.GET_IRON_NARRATOR_FLAG = true;
    }
    public void setFlagEnd() {
        this.GET_END_NARRATOR_FLAG = true;
    }

    public boolean asAlreadySpawn() {
        return this.JUST_SPAWN_NARRATOR_FLAG;
    }

    public boolean asAlreadyGetWood() {
        return this.GET_WOOD_NARRATOR_FLAG;
    }

    public boolean asAlreadyGetCoal() {
        return this.GET_COAL_NARRATOR_FLAG;
    }
    public boolean asAlreadyGetDiamond() {
        return this.GET_DIAMOND_NARRATOR_FLAG;
    }

    public boolean asAlreadyGetIron() {
        return this.GET_IRON_NARRATOR_FLAG;
    }

    public boolean asAlreadyTraveledToNether() {
        return this.GET_NETHER_NARRATOR_FLAG;
    }
    public boolean asAlreadyGetEyeOfEnder() {
        return this.GET_EYE_OF_ENDER_NARRATOR_FLAG;
    }
    public boolean asAlreadyGetEnderPearl() {
        return this.GET_ENDER_PEARL_NARRATOR_FLAG;
    }
    public boolean asAlreadyTraveledToEnd() {
        return this.GET_END_NARRATOR_FLAG;
    }


    public void saveNBTData(CompoundTag nbt) {
        nbt.putBoolean("just_spawn_narrator_flag", this.JUST_SPAWN_NARRATOR_FLAG);
        nbt.putBoolean("get_wood_narrator_flag", this.GET_WOOD_NARRATOR_FLAG);
        nbt.putBoolean("get_coal_narrator_flag", this.GET_COAL_NARRATOR_FLAG);
        nbt.putBoolean("get_iron_narrator_flag", this.GET_IRON_NARRATOR_FLAG);
        nbt.putBoolean("get_diamond_narrator_flag", this.GET_DIAMOND_NARRATOR_FLAG);
        nbt.putBoolean("get_nether_narrator_flag", this.GET_NETHER_NARRATOR_FLAG);
        nbt.putBoolean("get_ender_pearl_narrator_flag", this.GET_ENDER_PEARL_NARRATOR_FLAG);
        nbt.putBoolean("get_eye_of_ender_narrator_flag", this.GET_EYE_OF_ENDER_NARRATOR_FLAG);
        nbt.putBoolean("get_end_narrator_flag", this.GET_END_NARRATOR_FLAG);
    }

    public void loadNBTData(CompoundTag nbt) {
        this.JUST_SPAWN_NARRATOR_FLAG = nbt.getBoolean("just_spawn_narrator_flag");
        this.GET_WOOD_NARRATOR_FLAG = nbt.getBoolean("get_wood_narrator_flag");
        this.GET_COAL_NARRATOR_FLAG = nbt.getBoolean("get_coal_narrator_flag");
        this.GET_IRON_NARRATOR_FLAG = nbt.getBoolean("get_iron_narrator_flag");
        this.GET_DIAMOND_NARRATOR_FLAG = nbt.getBoolean("get_diamond_narrator_flag");
        this.GET_NETHER_NARRATOR_FLAG = nbt.getBoolean("get_nether_narrator_flag");
        this.GET_ENDER_PEARL_NARRATOR_FLAG = nbt.getBoolean("get_ender_pearl_narrator_flag");
        this.GET_EYE_OF_ENDER_NARRATOR_FLAG = nbt.getBoolean("get_eye_of_ender_narrator_flag");
        this.GET_END_NARRATOR_FLAG = nbt.getBoolean("get_end_narrator_flag");
    }



}
