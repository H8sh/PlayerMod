package net.h8sh.playermod.block.custom.linkzone.citadel;

import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToCitadelC2SPacket;
import net.h8sh.playermod.world.dimension.DimensionManager;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LinkZoneCitadelEastBlock extends Block {
    private static final VoxelShape VOXEL_SHAPE = Shapes.box(0, 0, 0, 1, 1, 1);

    public LinkZoneCitadelEastBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return VOXEL_SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Player player) {
            Direction direction = Direction.EAST;
            DimensionManager.setDirection(direction);
            player.sendSystemMessage(Component.literal("la direction est:" + direction).withStyle(ChatFormatting.AQUA));
            ModMessages.sendToServer(new OnChangedDimensionToCitadelC2SPacket());
        }
    }

}