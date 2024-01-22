package net.h8sh.playermod.world.dimension;

public enum DimensionMaps {

    CITADEL(new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}}),

    MINE(new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}}),

    FIELDS(new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}}),

    MANSION(new String[][]{{" ", " ", " "}, {" ", " ", " "}, {" ", " ", " "}});

    private String[][] map;

    DimensionMaps(String[][] map) {
        this.map = map;
    }

    public String[][] getMap() {
        return map;
    }

}
