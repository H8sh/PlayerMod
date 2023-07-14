package net.h8sh.playermod.capability.profession;

public class ProfessionData {

    private static int profession;

    public static void set(int profession) {
        ProfessionData.profession = profession;
    }

    public static int getPlayerProfession() {
        return profession;
    }

}
