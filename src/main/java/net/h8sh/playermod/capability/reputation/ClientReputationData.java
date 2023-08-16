package net.h8sh.playermod.capability.reputation;

public class ClientReputationData {

    public static int reputation = 0;
    public static float reputationProgress = 0;
    public static int reputationNeeded = 1;

    public static void set(int reputation, float reputationProgress, int reputationNeeded) {
        ClientReputationData.reputation = reputation;
        ClientReputationData.reputationProgress = reputationProgress;
        ClientReputationData.reputationNeeded = reputationNeeded;
    }

    public static int getReputationNeeded() {
        return reputationNeeded;
    }

    public static float getReputationProgress() {
        return reputationProgress;
    }

    public static int getReputation() {
        return reputation;
    }
}
