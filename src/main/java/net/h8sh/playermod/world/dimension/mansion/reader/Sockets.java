package net.h8sh.playermod.world.dimension.mansion.reader;

import com.google.gson.annotations.SerializedName;

public class Sockets implements Cloneable{

    @Override
    public Sockets clone() throws CloneNotSupportedException {
        return (Sockets) super.clone();
    }

    @SerializedName("north")
    public int north;
    @SerializedName("south")
    public int south;
    @SerializedName("east")
    public int east;
    @SerializedName("west")
    public int west;

    public int getEast() {
        return east;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public int getNorth() {
        return north;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public int getSouth() {
        return south;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public int getWest() {
        return west;
    }

    public void setWest(int west) {
        this.west = west;
    }


}
