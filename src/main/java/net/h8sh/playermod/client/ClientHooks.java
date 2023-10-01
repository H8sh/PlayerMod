package net.h8sh.playermod.client;

import net.h8sh.playermod.screen.pnj.PnjBlockScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public class ClientHooks {

    public static void openPnjBlockScreen(BlockPos pPos) {
        Minecraft.getInstance().setScreen(new PnjBlockScreen());
    }

}
