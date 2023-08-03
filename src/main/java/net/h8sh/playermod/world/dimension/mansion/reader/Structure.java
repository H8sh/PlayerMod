package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Structure implements Serializable {

    @JsonProperty("mesh")
    public Mesh mesh;
    @JsonProperty("rotation")
    public Rotation rotation;
    @JsonProperty("sockets")
    public Sockets sockets;
    @JsonProperty("name")
    public Name name;
    @JsonProperty("neighbors")
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
