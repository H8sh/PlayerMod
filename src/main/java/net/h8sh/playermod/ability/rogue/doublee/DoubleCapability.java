package net.h8sh.playermod.ability.rogue.doublee;

import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Optional;

@AutoRegisterCapability
public class DoubleCapability {

    public static boolean frizz;

    public static void setFrizzTarget(boolean frizzTarget) {
        frizz = frizzTarget;
    }

    public static boolean isEntityFrizz() {
        return frizz;
    }
    public static boolean targetMarkerOn = false;
    private static Entity toTarget;
    private static boolean onCD;
    private static boolean prepareTargetMark;
    private static boolean readyToUse;

    public static Entity setToTarget(Entity target) {
        return toTarget = target;
    }

    public static Entity getToTarget() {
        return toTarget;
    }

    public static boolean isTargetMarkerOn() {
        return targetMarkerOn;
    }

    public static void setTargetMarkerOn(boolean targetMarkerOn) {
        DoubleCapability.targetMarkerOn = targetMarkerOn;
    }

    public static boolean getPrepareTargetMark() {
        return DoubleCapability.prepareTargetMark;
    }

    public static void setPrepareTargetMark(boolean prepareTargetMark) {
        DoubleCapability.prepareTargetMark = prepareTargetMark;
    }

    public static boolean getReadyToUse() {
        return DoubleCapability.readyToUse;
    }

    public static void setReadyToUse(boolean readyToUse) {
        DoubleCapability.readyToUse = readyToUse;
    }

    public static boolean isOnCD() {
        return onCD;
    }

    public static void setOnCD(boolean onCD) {
        DoubleCapability.onCD = onCD;
    }

    public static Entity getEntityPlayerLookAt(Player player, Double range) {
        float playerRotX = player.getXRot();
        float playerRotY = player.getYRot();
        Vec3 eyePosition = player.getEyePosition();
        float f2 = Mth.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f3 = Mth.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        float f4 = -Mth.cos(-playerRotX * ((float) Math.PI / 180F));
        float additionY = Mth.sin(-playerRotX * ((float) Math.PI / 180F));
        float additionX = f3 * f4;
        float additionZ = f2 * f4;
        double selectedRange = range;
        Vec3 endVec = eyePosition.add((double) additionX * selectedRange, (double) additionY * selectedRange, (double) additionZ * selectedRange);
        AABB startEndBox = new AABB(eyePosition, endVec);
        Entity toReturn = null;
        for (Entity entity : player.level().getEntities(player, startEndBox, (val) -> true)) {
            AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
            Optional<Vec3> optional = aabb.clip(eyePosition, endVec);
            if (aabb.contains(eyePosition)) {
                if (selectedRange >= 0.0D) {
                    toReturn = entity;
                    eyePosition = optional.orElse(eyePosition);
                    selectedRange = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3 vec31 = optional.get();
                double d1 = eyePosition.distanceToSqr(vec31);
                if (d1 < selectedRange || selectedRange == 0.0D) {
                    if (entity.getRootVehicle() == player.getRootVehicle() && !entity.canRiderInteract()) {
                        if (selectedRange == 0.0D) {
                            toReturn = entity;
                            eyePosition = vec31;
                        }
                    } else {
                        toReturn = entity;
                        eyePosition = vec31;
                        selectedRange = d1;
                    }
                }
            }
        }

        return (toReturn == null) ? null : new EntityHitResult(toReturn).getEntity();

    }

    public static void spawnTargetMarkMarker(Player player) {
        Entity entity = DoubleCapability.getEntityPlayerLookAt(player, 100D);
        if (entity != null) {
            entity.animateHurt(1.0F);
            setToTarget(entity);
        }
    }

    public static void teleportToTarget(Entity toTarget, Player player) {
        Direction direction = toTarget.getDirection();
        CrystalEntity crystal1 = ModEntities.CRYSTAL.get().create(player.level());
        CrystalEntity crystal2 = ModEntities.CRYSTAL.get().create(player.level());
        CrystalEntity crystal3 = ModEntities.CRYSTAL.get().create(player.level());
        CrystalEntity crystal4 = ModEntities.CRYSTAL.get().create(player.level());
        CrystalEntity crystal5 = ModEntities.CRYSTAL.get().create(player.level());
        CrystalEntity crystal6 = ModEntities.CRYSTAL.get().create(player.level());
        CrystalEntity crystal7 = ModEntities.CRYSTAL.get().create(player.level());
        switch (direction) {
            case NORTH:
                player.teleportTo(toTarget.getX(), toTarget.getY(), toTarget.getZ() - 5);
                crystal1.setPos(toTarget.getX(), toTarget.getY(), toTarget.getZ() + 5);
                crystal2.setPos(toTarget.getX() - 5, toTarget.getY(), toTarget.getZ());
                crystal3.setPos(toTarget.getX() + 5, toTarget.getY(), toTarget.getZ());

                player.level().addFreshEntity(crystal1);
                player.level().addFreshEntity(crystal2);
                player.level().addFreshEntity(crystal3);
                break;
            case SOUTH:
                player.teleportTo(toTarget.getX(), toTarget.getY(), toTarget.getZ() + 5);
                crystal1.setPos(toTarget.getX(), toTarget.getY(), toTarget.getZ() - 5);
                crystal2.setPos(toTarget.getX() - 5, toTarget.getY(), toTarget.getZ());
                crystal3.setPos(toTarget.getX() + 5, toTarget.getY(), toTarget.getZ());
                player.level().addFreshEntity(crystal1);
                player.level().addFreshEntity(crystal2);
                player.level().addFreshEntity(crystal3);

                break;
            case EAST:
                player.teleportTo(toTarget.getX() + 5, toTarget.getY(), toTarget.getZ());
                crystal1.setPos(toTarget.getX(), toTarget.getY(), toTarget.getZ() + 5);
                crystal2.setPos(toTarget.getX(), toTarget.getY(), toTarget.getZ() - 5);
                crystal3.setPos(toTarget.getX() + 5, toTarget.getY(), toTarget.getZ());
                player.level().addFreshEntity(crystal1);
                player.level().addFreshEntity(crystal2);
                player.level().addFreshEntity(crystal3);

                break;
            case WEST:
                player.teleportTo(toTarget.getX() - 5, toTarget.getY(), toTarget.getZ());
                crystal1.setPos(toTarget.getX(), toTarget.getY(), toTarget.getZ() + 5);
                crystal2.setPos(toTarget.getX() - 5, toTarget.getY(), toTarget.getZ());
                crystal3.setPos(toTarget.getX(), toTarget.getY(), toTarget.getZ() - 5);
                player.level().addFreshEntity(crystal1);
                player.level().addFreshEntity(crystal2);
                player.level().addFreshEntity(crystal3);

                break;
        }

    }

    public void copyFrom(DoubleCapability source) {
        DoubleCapability.prepareTargetMark = source.prepareTargetMark;
        DoubleCapability.readyToUse = source.readyToUse;
        DoubleCapability.onCD = source.onCD;

    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("prepareTargetMark", prepareTargetMark);
        compoundTag.putBoolean("readyToUse", readyToUse);
        compoundTag.putBoolean("onCD", onCD);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        prepareTargetMark = compoundTag.getBoolean("prepareTargetMark");
        readyToUse = compoundTag.getBoolean("readyToUse");
        onCD = compoundTag.getBoolean("onCD");
    }


    public void spawnLeviathan(ServerLevel level) {


    }
}