package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Mesh {
    @SerializedName("mesh_type")
    public String mesh_type;

    public String getMesh_type() {
        return mesh_type;
    }

    public void setMesh_type(String mesh_type) {
        this.mesh_type = mesh_type;
    }

}
