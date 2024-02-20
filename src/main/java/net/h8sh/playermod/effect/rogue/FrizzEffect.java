package net.h8sh.playermod.effect.rogue;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.CampfireBlock;

public class FrizzEffect extends MobEffect {

    public FrizzEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
    if (!pLivingEntity.level().isClientSide()){
        Double x = pLivingEntity.getX();
        Double y = pLivingEntity.getY();
        Double z = pLivingEntity.getY();

        pLivingEntity.teleportTo(x, y, z);
        pLivingEntity.setDeltaMovement(0,0,0);
    }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}
