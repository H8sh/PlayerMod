package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Structure implements Cloneable{

    @Override
    public Structure clone() throws CloneNotSupportedException {
        Structure cloned = (Structure) super.clone();

        cloned.setMesh(cloned.getMesh().clone());
        cloned.setName(cloned.getName().clone());
        cloned.setRotation(cloned.getRotation().clone());
        cloned.setSockets(cloned.getSockets().clone());

        List<Neighbors> clonedNeighbors = new ArrayList<>();

        for (Neighbors neighbors : cloned.getNeighbors()) {
            neighbors.clone();
            clonedNeighbors.add(neighbors);
        }
        cloned.setNeighbors(clonedNeighbors);

        return cloned;

    }

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

    public void setSockets(Sockets sockets) {
        this.sockets = sockets;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setNeighbors(List<Neighbors> neighbors) {
        this.neighbors = neighbors;
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
