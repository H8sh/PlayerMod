package net.h8sh.playermod.ability.wizard.mana;

public class ClientManaData {

    private static int playerMana;
    private static int chunkMana;

    public static int getPlayerMana() {
        return playerMana;
    }

    public static void setPlayerMana(int playerMana) {
        ClientManaData.playerMana = playerMana;
    }

    public static int getChunkMana() {
        return chunkMana;
    }

    public static void setChunkMana(int chunkMana) {
        ClientManaData.chunkMana = chunkMana;
    }
}
