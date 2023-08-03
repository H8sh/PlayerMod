package net.h8sh.playermod.block.custom;

import net.h8sh.playermod.networking.travelling.TravelManager;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.travelling.OnChangedDimensionToWonderlandsHomeC2SPacket;
import net.h8sh.playermod.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class PortalBlock extends Block {

    private static final VoxelShape SHAPE = Shapes.box(0, 0, 0, 1, .8, 1);

    public PortalBlock(Properties properties) {
        super(properties);
    }


    @NotNull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ServerPlayer player) {
            if (!level.dimension().equals(ModDimensions.WONDERLANDS_KEY)) {
                ModMessages.sendToServer(new OnChangedDimensionToWonderlandsHomeC2SPacket());
            }
            if (!level.dimension().equals(Level.OVERWORLD)) {
                TravelManager.teleportTo(player, pos.north(), Level.OVERWORLD);
            }
        }
    }



}
