package net.h8sh.playermod.capability.narrator;

public class ClientNarratorData {

    private static String narrator;
    private static String message;

    public static void setNarrator(String narrator) {
        ClientNarratorData.narrator = narrator;
    }

    public static void setMessage(String message) {
        ClientNarratorData.message = message;
    }

    public static String getNarrator() {
        return narrator;
    }

    public static String getMessage() {
        return message;
    }
}
