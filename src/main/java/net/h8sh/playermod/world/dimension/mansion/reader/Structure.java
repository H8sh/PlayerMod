package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Structure {

    @SerializedName("mesh")
    public Mesh mesh;
    @SerializedName("rotation")
    public Rotation rotation;
    @SerializedName("sockets")
    public Sockets sockets;
    @SerializedName("name")
    public Name name;
    @SerializedName("neighbors")
    public List<Neighbors> neighbors;

    public List<Neighbors> getNeighbors() {
        return neighbors;
    }

    public Mesh getMesh() {
        return mesh;
    }
    public Rotation getRotation() {
        return rotation;
    }
    public Sockets getSockets() {
        return sockets;
    }

    public Name getName() {
        return name;
    }

}
