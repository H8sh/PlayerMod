package net.h8sh.playermod.networking.classes.wizard.mana;

import net.h8sh.playermod.ability.wizard.mana.ManaCapability;
import net.h8sh.playermod.ability.wizard.mana.ManaCapabilityProvider;
import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ManaExtractionC2SPacket {

    public ManaExtractionC2SPacket() {
    }

    public ManaExtractionC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();


            player.getCapability(ManaCapabilityProvider.PLAYER_MANA).ifPresent(manaCapability -> {

                ChunkPos chunkPos = level.getChunkAt(player.blockPosition()).getPos();
                int extractedMana = ManaCapability.extractMana(chunkPos);
                manaCapability.setPlayerMana(manaCapability.getPlayerMana() + extractedMana);

                BlockPos pos = chunkPos.getMiddleBlockPosition((int) player.getY());

                if (ManaCapability.getManaInChunk(chunkPos) == 0 && CrystalCapability.isCrystal(pos)) {
                    UUID uuid = CrystalCapability.spawnCrystal(new BlockPos(pos.getX(), pos.getY() + 4, pos.getZ()), level);
                    CrystalCapability.setTimer(new BlockPos(pos.getX(), pos.getY() + 4, pos.getZ()), level, uuid, player);
                }
            });
        });

        return true;
    }

}
