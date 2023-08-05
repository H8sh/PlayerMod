package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Prototypes {

    @SerializedName("prototypes")
    public List<Prototype> prototype;

    public List<Prototype> getPrototype() {
        return prototype;
    }

    public void setPrototype(List<Prototype> prototype) {
        this.prototype = prototype;
    }
}
