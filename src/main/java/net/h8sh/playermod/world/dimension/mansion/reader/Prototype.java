package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Prototype implements Serializable {
    @JsonProperty("structure")
    public Structure structure;

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}