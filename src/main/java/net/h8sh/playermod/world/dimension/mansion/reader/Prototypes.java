package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Prototypes implements Serializable {

    @JsonProperty("prototypes")
    public List<Prototype> prototype;

    public List<Prototype> getPrototype() {
        return prototype;
    }

    public void setPrototype(List<Prototype> prototype) {
        this.prototype = prototype;
    }
}
