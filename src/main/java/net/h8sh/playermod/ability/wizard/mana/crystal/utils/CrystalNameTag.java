package net.h8sh.playermod.ability.wizard.mana.crystal.utils;

import net.h8sh.playermod.networking.classes.wizard.manapacket.crystal.ResetCrystalS2CPacket;
import net.h8sh.playermod.ability.wizard.mana.ManaManager;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.h8sh.playermod.networking.ModMessages;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;

public class CrystalNameTag extends Thread {

    private static CrystalEntity crystal;

    private int counter = 15; //seconds TODO: - passive upgrade timer

    public CrystalNameTag(CrystalEntity crystal) {
        this.crystal = crystal;
    }

    @Override
    public void run() {
        for (int i = 0; i < 60; i++) {
            counter--;
            if (counter <= 0) {

                ManaManager.get(crystal.level()).setManaInternal(crystal.blockPosition());
                ModMessages.sendToServer(new ResetCrystalS2CPacket(crystal.blockPosition()));
                crystal.remove(Entity.RemovalReason.UNLOADED_TO_CHUNK);

                return;
            }
            int minutes = counter / 60;
            int seconds = counter % 60;

            String timeString = String.format("%d:%02d", minutes, seconds);

            crystal.setCustomName(Component.literal("Crystal" + timeString + ")"));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
