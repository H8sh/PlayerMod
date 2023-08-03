package net.h8sh.playermod.networking.classes.wizard.aoepacket;

import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MagicAoES2CPacket {

    public MagicAoES2CPacket() {

    }

    public MagicAoES2CPacket(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //server side
            ServerPlayer player = context.getSender();

            List<Entity> crystals = CrystalManager.removeLivingCrystals(player, player.level());
            for (Entity entity : crystals) {
                entity.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);
            }
        });

        return true;
    }

}