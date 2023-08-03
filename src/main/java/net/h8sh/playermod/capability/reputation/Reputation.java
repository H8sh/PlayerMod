package net.h8sh.playermod.capability.reputation;

import net.h8sh.playermod.PlayerMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class Reputation {
    private static int reputation;


    public static int getReputation() {
        return reputation;
    }


    public void addProfession(int reputation) {
        this.reputation += reputation;
    }

    public void resetProfession() {
        this.reputation = 0;
    }

    public void copyFrom(Reputation source) {
        this.reputation = source.reputation;
    }

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("reputation", reputation);
    }

    public void loadNBTData(CompoundTag nbt) {
        reputation = nbt.getInt("reputation");
    }

}
