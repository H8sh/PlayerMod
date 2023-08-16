package net.h8sh.playermod.capability.reputation;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Reputation {
    private static int reputation;
    private float reputationProgress;

    public int getXpNeededForNextLevel() {
        if (this.reputation >= 30) {
            return 112 + (this.reputation - 30) * 9;
        } else {
            return this.reputation >= 15 ? 37 + (this.reputation - 15) * 5 : 7 + this.reputation * 2;
        }
    }

    public void giveExperiencePoints(int pXpPoints) {
        this.reputationProgress += (float)pXpPoints / (float)this.getXpNeededForNextLevel();
    }

    public void copyFrom(Reputation source) {
        this.reputation = source.reputation;
        this.reputationProgress = source.reputationProgress;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("reputation", reputation);
        nbt.putFloat("reputation_progress", reputationProgress);
    }

    public void loadNBTData(CompoundTag nbt) {
        reputation = nbt.getInt("reputation");
        reputationProgress = nbt.getFloat("reputation_progress");
    }

}
