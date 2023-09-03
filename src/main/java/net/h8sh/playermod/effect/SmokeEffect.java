package net.h8sh.playermod.effect;

import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.CampfireBlock;

public class SmokeEffect extends MobEffect {

    public SmokeEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        RandomSource randomsource = pLivingEntity.level().random;
        if (randomsource.nextFloat() < 0.11F) {
            for (int i = 0; i < randomsource.nextInt(2) + 10; ++i) {
                CampfireBlock.makeParticles(pLivingEntity.level(), pLivingEntity.blockPosition(), true, false);
            }
        }

        int l = 3;

        for (int j = 0; j < 10; ++j) {
            if (randomsource.nextFloat() < 0.2F) {
                Direction direction = Direction.from2DDataValue(Math.floorMod(j + l, 4));
                double d0 = pLivingEntity.getX() + 0.5D - (double) ((float) direction.getStepX() * 0.3125F) + (double) ((float) direction.getClockWise().getStepX() * 0.3125F);
                double d1 = pLivingEntity.getY() + 0.5D;
                double d2 = pLivingEntity.getZ() + 0.5D - (double) ((float) direction.getStepZ() * 0.3125F) + (double) ((float) direction.getClockWise().getStepZ() * 0.3125F);

                for (int k = 0; k < 4; ++k) {
                    pLivingEntity.level().addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0D, 5.0E-4D, 0.0D);
                }
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

}
