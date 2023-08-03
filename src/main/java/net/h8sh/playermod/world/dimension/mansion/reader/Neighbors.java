package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Neighbors implements Serializable {

    @JsonProperty("northNeighbor")
    public List<String> northNeighbor;

    @JsonProperty("southNeighbor")
    public List<String> southNeighbor;

    @JsonProperty("eastNeighbor")
    public List<String> eastNeighbor;

    @JsonProperty("westNeighbor")
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
