package net.h8sh.playermod.block.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.level.levelgen.feature.GlowstoneFeature;

public class DeathBlock extends Block {

    public DeathBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter pLevel, Entity pEntity) {
        if (pEntity instanceof Player player) {
            player.kill();
        }
    }
}
