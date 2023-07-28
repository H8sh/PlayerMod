package net.h8sh.playermod.ability.wizard.mana.crystal;

public class ClientCrystalData {

    private static int playerCrystal;

    public static void set(int playerCrystal){
        ClientCrystalData.playerCrystal = playerCrystal;
    }

    public static int getPlayerCrystal(){
        return playerCrystal;
    }

}
