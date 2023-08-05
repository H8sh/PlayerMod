package net.h8sh.playermod.world.dimension.mansion.reader;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Prototypes implements Cloneable{

    @Override
    public Prototypes clone() throws CloneNotSupportedException {
        Prototypes cloned = (Prototypes) super.clone();

        List<Prototype> clonedPrototype = new ArrayList<>();

        for (Prototype prototype : cloned.getPrototype()) {
            prototype.clone();
            clonedPrototype.add(prototype);
        }
        cloned.setPrototype(clonedPrototype);

        return cloned;
    }

    @SerializedName("prototypes")
    public List<Prototype> prototype;

    public List<Prototype> getPrototype() {
        return prototype;
    }

    public void setPrototype(List<Prototype> prototype) {
        this.prototype = prototype;
    }
}
