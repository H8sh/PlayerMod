package net.h8sh.playermod.block.custom;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import net.h8sh.playermod.block.entity.AdamBlockEntity;
import net.h8sh.playermod.block.entity.PlinthBlockEntity;
import net.h8sh.playermod.capability.travel.Travel;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToWonderlandsHomeC2SPacket;
import net.h8sh.playermod.world.dimension.ModDimensions;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class PlinthBLock extends BaseEntityBlock {
    private static final VoxelShape SHAPE = Shapes.box(-128,0,-128,128,0,128);

    public PlinthBLock(Properties properties) {
        super(properties);
    }

    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new PlinthBlockEntity(pPos,pState);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
