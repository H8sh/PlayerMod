package net.h8sh.playermod.entity.custom.gunslinger;

import net.h8sh.playermod.block.ModBlocks;
import net.h8sh.playermod.block.custom.SniperBlock;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.item.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpyglassItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public class Sniper extends ThrowableItemProjectile {

    public Sniper(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Sniper(Level pLevel) {
        super(ModEntities.SNIPER.get(), pLevel);
    }

    public Sniper(Level pLevel, LivingEntity livingEntity) {
        super(ModEntities.SNIPER.get(), livingEntity, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.SNIPER_ITEM.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if (!this.level().isClientSide()) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.level().setBlock(blockPosition(),((SniperBlock) ModBlocks.SNIPER_BLOCK.get()).getRandomBlockState(), 3);
        }

        this.discard();

        super.onHitBlock(pResult);
    }


}
