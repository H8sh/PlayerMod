package net.h8sh.playermod.capability.narrator;

public class NarratorManager {

    public static String JUST_SPAWN_NARRATOR = "Ha... Again...";
    public static String GET_WOOD_NARRATOR = "Let's see... A crafting table next, han ?";
    public static String GET_COAL_NARRATOR = "Now iron...";
    public static String GET_SMELTING_NARRATOR = "I used to like the smell...";
    public static String GET_DIAMOND_NARRATOR = "Need lava, i guess...";
    public static String GET_NETHER_NARRATOR = "Hellishly hot, as always...";
    public static String GET_ENDER_PEARL_NARRATOR = "I hate those noises";
    public static String GET_END_NARRATOR = "The Dragon's lair...";
    public static String GET_EYE_OF_ENDER_NARRATOR_PART1 = "In the blink of an eye";
    public static String GET_EYE_OF_ENDER_NARRATOR_PART2 = "What was that ?";
    public static String WONDERLANDS_NARRATOR = "#@%$&~!";
    public static String STEVE_NARRATOR = "Steve: ";
    public static String UNKNOWN_NARRATOR = "Unknown: ";
    public static String EMPTY_NARRATOR = "";
    private static boolean wonderlandsTalking;

    public static void isWonderlandsTalkingToPlayer(boolean bol) {
        wonderlandsTalking = bol;
    }

    public static boolean wonderlandsTalkingToPlayer() {
        return wonderlandsTalking;
    }

}
