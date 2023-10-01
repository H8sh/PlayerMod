package net.h8sh.playermod.ability.druid.firemeta.fire_aura;

import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@AutoRegisterCapability
public class FireAuraCapability {

    private static boolean onFireAura;
    private static boolean entitiesInWorld;

    public static boolean getEntitiesInWorld() {
        return FireAuraCapability.entitiesInWorld;
    }

    public static void setEntitiesInWorld(boolean entitiesInWorld) {
        FireAuraCapability.entitiesInWorld = entitiesInWorld;
    }

  /*  private static List<CrystalEntity> crystalEntities = new ArrayList<>();*/

 /*   public static List<CrystalEntity> getCrystalEntities() {
        return crystalEntities;
    }*/

    public static boolean getOnFireAura() {
        return FireAuraCapability.onFireAura;
    }

    public static void setOnFireAura(boolean onFireAura) {
        FireAuraCapability.onFireAura = onFireAura;
    }

/*
    public static void createCrystals(Level level) {
        if (crystalEntities.isEmpty()) {

            CrystalEntity crystalEntity1 = ModEntities.CRYSTAL.get().create(level);
            CrystalEntity crystalEntity2 = ModEntities.CRYSTAL.get().create(level);
            CrystalEntity crystalEntity3 = ModEntities.CRYSTAL.get().create(level);
            CrystalEntity crystalEntity4 = ModEntities.CRYSTAL.get().create(level);

            crystalEntities.add(crystalEntity1);
            crystalEntities.add(crystalEntity2);
            crystalEntities.add(crystalEntity3);
            crystalEntities.add(crystalEntity4);
        }
    }
*/

    public static void addFreshEntities(Player player, Level level, List<CrystalEntity> crystalEntities) {
        for (CrystalEntity crystal : crystalEntities) {
            level.addFreshEntity(crystal);
            crystal.setPos(player.position());
        }
    }

    public static void saveEntity(CompoundTag compoundTag, Entity entity) {
        if (!compoundTag.contains("entities")) {
            compoundTag.put("entities", new ListTag());
        }
        ListTag listtag = compoundTag.getList("entities", Tag.TAG_COMPOUND);
        listtag.add(entity.saveWithoutId(compoundTag));
        compoundTag.put("entities", listtag);

        entity.remove(Entity.RemovalReason.DISCARDED);
    }

    public static List<CrystalEntity> loadEntities(CompoundTag compoundTag, Level level) {
        List<CrystalEntity> crystalEntities = new ArrayList<>();
        if (!compoundTag.contains("entities"))
            return null;
        ListTag listtag = compoundTag.getList("entities", Tag.TAG_COMPOUND);
        Stream<Entity> entities = EntityType.loadEntitiesRecursive(listtag, level);
        entities.forEach(entity -> crystalEntities.add((CrystalEntity) entity));
        return crystalEntities;
    }

    public void copyFrom(FireAuraCapability source) {
        FireAuraCapability.onFireAura = source.onFireAura;
        FireAuraCapability.entitiesInWorld = source.entitiesInWorld;

        List<CrystalEntity> copyList = new ArrayList<>();
    /*    for (CrystalEntity crystal : source.crystalEntities) {
            copyList.add(crystal);
        }
        FireAuraCapability.crystalEntities = copyList;*/
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onFireAura", onFireAura);
        compoundTag.putBoolean("entitiesInWorld", entitiesInWorld);
       /* for (CrystalEntity entity : crystalEntities) {
            saveEntity(compoundTag, entity);
        }*/
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onFireAura = compoundTag.getBoolean("onFireAura");
        entitiesInWorld = compoundTag.getBoolean("entitiesInWorld");
 /*       crystalEntities = loadEntities(compoundTag, Minecraft.getInstance().level);*/

    }
}