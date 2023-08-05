package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rotation implements Cloneable {

    @Override
    public Rotation clone() throws CloneNotSupportedException {
        return (Rotation) super.clone();
    }

    @SerializedName("rotation_type")
    public int rotation_type;
    @SerializedName("rotation_allowed")
    public int rotation_allowed;

    public int getRotation_allowed() {
        return rotation_allowed;
    }

    public void setRotation_allowed(int rotation_allowed) {
        this.rotation_allowed = rotation_allowed;
    }

    public int getRotation_type() {
        return rotation_type;
    }

    public void setRotation_type(int rotation_type) {
        this.rotation_type = rotation_type;
    }

}
