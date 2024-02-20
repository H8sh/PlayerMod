package net.h8sh.playermod.ability.gunslinger.sniper;

import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nullable;
import java.util.Optional;

@AutoRegisterCapability
public class SniperCapability {

    private static LivingEntity target;

    private static final int DURATION = Mth.ceil(60.0F);
    private static final double HORIZONTAL_DISTANCE = 15.0D;
    private static final double VERTICAL_DISTANCE = 20.0D;
    private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.ceil(34.0D);
    private static boolean isTargeting;

    @Contract("null->false")
    public static boolean canTargetEntity(@Nullable Entity entity) {
        if (entity instanceof LivingEntity livingentity && Minecraft.getInstance().player != null) {
            Player player = Minecraft.getInstance().player;
            if (player.level() == entity.level() && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) && !player.isAlliedTo(entity) && livingentity.getType() != EntityType.ARMOR_STAND && livingentity.getType() != EntityType.WARDEN && !livingentity.isInvulnerable() && !livingentity.isDeadOrDying() && player.level().getWorldBorder().isWithinBounds(livingentity.getBoundingBox())) {
                return true;
            }
        }

        return false;
    }

    public void copyFrom(SniperCapability source) {
        SniperCapability.isTargeting = source.isTargeting;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("isTeleporting", isTargeting);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        isTargeting = compoundTag.getBoolean("isTeleporting");
    }

    protected boolean checkExtraStartConditions(LivingEntity target, Player pOwner) {
        return pOwner.closerThan(target, HORIZONTAL_DISTANCE, VERTICAL_DISTANCE);
    }

    protected void start(Player pEntity) {
        //TODO: add animation of load
        pEntity.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
    }

    public void tick(ServerLevel pLevel, Player pOwner, LivingEntity target) {
        if (target == null) {
            return;
        }

        pOwner.lookAt(EntityAnchorArgument.Anchor.EYES, target.position());

        if (true) {//TODO if delay + cooldown ok + key spell down + check extra condition

            /*pOwner.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, (long) (DURATION - TICKS_BEFORE_PLAYING_SOUND));*/ //TODO reset timing
            start(pOwner);


            if (SniperCapability.canTargetEntity(target)) { //TODO check if cooldown of start animation is down
                Vec3 vec3 = pOwner.position().add(0.0D, (double) 1.6F, 0.0D);
                Vec3 vec31 = target.getEyePosition().subtract(vec3);
                Vec3 vec32 = vec31.normalize();

                for (int i = 1; i < Mth.floor(vec31.length()) + 7; ++i) {
                    Vec3 vec33 = vec3.add(vec32.scale((double) i));
                    pLevel.sendParticles(ParticleTypes.SONIC_BOOM, vec33.x, vec33.y, vec33.z, 1, 0.0D, 0.0D, 0.0D, 0.0D); //TODO change particle ?
                }

                pOwner.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F); //TODO change sound
                target.hurt(pLevel.damageSources().sonicBoom(pOwner), 10.0F); //TODO add custom damage source ?

                //TODO add conditions if custom mobs are able to knock-back (boss can't be push)

                double d1 = 0.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                double d0 = 2.5D * (1.0D - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                target.push(vec32.x() * d0, vec32.y() * d1, vec32.z() * d0);
            }
        }
    }

    public LivingEntity getTarget() {
        return target;
    }

    public static void setTarget(LivingEntity target) {
        SniperCapability.target = target;
    }

    public static LivingEntity getEntityPlayerLookAt(Player player, Double range) {
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

        return (toReturn == null) ? null : (LivingEntity) new EntityHitResult(toReturn).getEntity();

    }

    public static void spawnTargetMarkMarker(Player player) {
        LivingEntity entity = SniperCapability.getEntityPlayerLookAt(player, 100D);
        entity.getUUID();
        if (entity != null) {
            entity.animateHurt(1.0F);
            setTarget(entity);
        }
    }
}