package net.h8sh.playermod.capability.questing.reader;

import com.google.gson.annotations.SerializedName;
import net.h8sh.playermod.PlayerMod;
import net.minecraft.resources.ResourceLocation;

public class Quest {

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("id")
    public String id;

    @SerializedName("texture")
    public String texture;

    @SerializedName("parentId")
    public String parentId;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return Integer.parseInt(id);
    }

    public int getParentId() {
        return Integer.parseInt(parentId);
    }

    public ResourceLocation getTexture() {
        return new ResourceLocation(PlayerMod.MODID, texture);
    }
}
