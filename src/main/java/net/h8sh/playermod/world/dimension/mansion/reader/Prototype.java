package net.h8sh.playermod.world.dimension.mansion.reader;

import com.google.gson.annotations.SerializedName;

public class Prototype implements Cloneable {

    @Override
    public Prototype clone() throws CloneNotSupportedException {
        Prototype cloned = (Prototype) super.clone();
        cloned.setStructure(cloned.getStructure().clone());
        return cloned;
    }

    @SerializedName("structure")
    public Structure structure;

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }
}