package net.h8sh.playermod.networking.travelling;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OnChangedDimensionToMansionHuntedC2SPacket {

    public OnChangedDimensionToMansionHuntedC2SPacket() {
    }

    public OnChangedDimensionToMansionHuntedC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();

            //ResourceKey<Level> destination = ModDimensions.HUNTED_MANSION_KEY;
            ResourceKey<Level> destination = Level.OVERWORLD;
            ServerLevel newWorld = player.getServer().getLevel(destination);

            //TravelManager.teleport(player, newWorld, new BlockPos(0, 0, 0), true);
            //if (!TravelManager.get(player.serverLevel()).asAlreadyTravel("hunted_mansion")) {

            //var mansion = MansionManager.createMansion(MansionManager.getSize(), MansionManager.getSize());
            //var mansion = WaveCollapseManager.createMansion(MansionManager.getSize(), MansionManager.getSize());
            //Random random = new Random();

        /*        for (int i = 0; i < MansionManager.getSize(); i++) {
                    for (int j = 0; j < MansionManager.getSize(); j++) {*/

            //mansion[i][j] = random.nextInt(MansionManager.MANSION_MAX_SIZE_STRUCTURE - MansionManager.MANSION_MIN_SIZE_STRUCTURE + 1) + MansionManager.MANSION_MIN_SIZE_STRUCTURE;
            //mansion[i][j] = 0;

            /*BlockPos pos = new BlockPos(player.blockPosition().getX() - j * 16, player.blockPosition().getY(), player.blockPosition().getZ() - i * 16);*/

            //StructureTemplate template = newWorld.getStructureManager().getOrCreate(new ResourceLocation(PlayerMod.MODID, mansion[i][j]));
            //template.placeInWorld(newWorld, pos, pos, new StructurePlaceSettings(), newWorld.getRandom(), Block.UPDATE_ALL);

            //TravelManager.createPortal(newWorld, pos);

                /*    }
                }*/
                /*player.teleportTo(player.position().x + 0.5, player.position().y, player.position().z + 0.5);

                player.sendSystemMessage(Component.literal("Here for the first time").withStyle(ChatFormatting.AQUA));

                TravelManager.get(player.serverLevel()).usedToTravel("hunted_mansion", player.blockPosition());
*/
           /* } else {
                BlockPos pos = TravelManager.get(player.serverLevel()).getPos("hunted_mansion");

                player.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5); //BlockPos cast player coords to integers so we have to add 0.5 again for centering the player

                player.sendSystemMessage(Component.literal("Already been there").withStyle(ChatFormatting.RED));
            }*/

        });

        return true;
    }

}
