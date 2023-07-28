package net.h8sh.playermod.ability.networking.wizard.manapacket;

import net.h8sh.playermod.ability.wizard.mana.ManaManager;
import net.h8sh.playermod.ability.wizard.mana.crystal.ClientCrystalData;
import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalManager;
import net.h8sh.playermod.capability.ability.wizard.mana.ManaCapabilityProvider;
import net.h8sh.playermod.capability.ability.wizard.mana.crystal.CrystalCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;


public class PacketGatherMana {

    public static final String MESSAGE_NO_MANA = "message.wonderlands.no_mana";

    public PacketGatherMana() {

    }

    public PacketGatherMana(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf) {

    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            //server side
            ServerPlayer player = context.getSender();

            if (ClientCrystalData.getPlayerCrystal() < 5) {
                //Mana from the current chunk
                int extracted = ManaManager.get(player.level()).extractMana(player.blockPosition());

                //Set the crystal flag to 0
                CrystalManager.get(player.level()).setCrystalHashMapToZero(player.blockPosition());


                player.sendSystemMessage(Component.literal("crystal value:" + CrystalManager.get(player.level()).getCrystal(player.blockPosition()).getCrystal()));


                //if there is no more mana and the flag is set to 0 (the crystal hasn't spawned yet)
                if (extracted <= 0 && CrystalManager.get(player.level()).getCrystal(player.blockPosition()).getCrystal() == 0) {

                    //Set crystal data to +1
                    player.getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(playerCrystal -> {
                        playerCrystal.addCrystal(CrystalManager.get(player.level()).extractCrystal());
                    });

                    //set the crystal flag to 1
                    CrystalManager.get(player.level()).setCrystalFlag(player.blockPosition());
                    //spawn the crystal
                    CrystalManager.get(player.level()).spawnCrystal(player.blockPosition(), player, (ServerLevel) player.level());
                    //Announce to the player there is no more mana
                    player.sendSystemMessage(Component.translatable(MESSAGE_NO_MANA).withStyle(ChatFormatting.DARK_RED));

                } else {
                    player.getCapability(ManaCapabilityProvider.PLAYER_MANA).ifPresent(playerMana -> {
                        playerMana.addMana(extracted);
                    });

                }
            } else {
                player.sendSystemMessage(Component.literal("To many crystals !"));
            }

        });

        return true;
    }

}
