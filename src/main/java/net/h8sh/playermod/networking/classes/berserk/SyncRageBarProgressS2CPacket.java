package net.h8sh.playermod.networking.classes.berserk;

import net.h8sh.playermod.ability.berserk.ClientRageBarManager;
import net.h8sh.playermod.ability.druid.firemeta.damage_spell.DamageSpellCapability;
import net.h8sh.playermod.ability.druid.firemeta.damage_spell.DamageSpellCapabilityProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SyncRageBarProgressS2CPacket {

    private final float rageBar;

    public SyncRageBarProgressS2CPacket(float rageBar) {
        this.rageBar = rageBar;
    }

    public SyncRageBarProgressS2CPacket(FriendlyByteBuf byteBuf) {
        rageBar = byteBuf.readFloat();

    }

    public void toBytes(FriendlyByteBuf byteBuf) {
        byteBuf.writeFloat(rageBar);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // Client side

            ClientRageBarManager.setRageBar(rageBar);

        });

        return true;
    }

}
