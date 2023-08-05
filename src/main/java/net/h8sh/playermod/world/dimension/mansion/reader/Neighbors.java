package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Neighbors  {

    @SerializedName("northNeighbor")
    public List<String> northNeighbor;

    @SerializedName("southNeighbor")
    public List<String> southNeighbor;

    @SerializedName("eastNeighbor")
    public List<String> eastNeighbor;

    @SerializedName("westNeighbor")
    public List<String> westNeighbor;

    public Neighbors(){
        super();
    }

    public List<String> getWestNeighbor() {
        return westNeighbor;
    }

    public List<String> getSouthNeighbor() {
        return southNeighbor;
    }

    public List<String> getNorthNeighbor() {
        return northNeighbor;
    }

    public List<String> getEastNeighbor() {
        return eastNeighbor;
    }

}
