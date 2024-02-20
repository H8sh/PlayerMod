package net.h8sh.playermod.networking.classes.druid.firemeta.fireaura;

import net.h8sh.playermod.ability.druid.firemeta.fire_aura.FireAuraCapability;
import net.h8sh.playermod.ability.druid.firemeta.fire_aura.FireAuraCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FireAuraC2SPacket {

    public FireAuraC2SPacket() {
    }

    public FireAuraC2SPacket(FriendlyByteBuf byteBuf) {

    }

    public void toBytes(FriendlyByteBuf byteBuf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Server side

            ServerPlayer player = context.getSender();
            ServerLevel level = player.serverLevel();

            if (!FireAuraCapability.getOnFireAura()) {

                player.getCapability(FireAuraCapabilityProvider.PLAYER_FIREAURA).ifPresent(fireAuraCapability -> {
/*
                    FireAuraCapability.createCrystals(level);
                    if (!FireAuraCapability.getEntitiesInWorld()) {
                        FireAuraCapability.addFreshEntities(player,level, FireAuraCapability.getCrystalEntities());
                        FireAuraCapability.setEntitiesInWorld(true);
                    }*/


                    FireAuraCapability.setOnFireAura(true);
                });
            }
        });

        return true;
    }

}
