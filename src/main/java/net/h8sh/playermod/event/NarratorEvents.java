package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.capability.narrator.NarratorManager;
import net.h8sh.playermod.capability.narrator.NarratorProvider;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.packet.narrator.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class NarratorEvents {

    private static boolean sendEmptyMessage = true;
    private static boolean spawnFlag = true;

    @SubscribeEvent
    public static void onPlayerGettingLOGForFirstTime(PlayerEvent.ItemPickupEvent event) {

        event.getEntity().getCapability(NarratorProvider.NARRATOR).ifPresent(narrator -> {

            if ((event.getStack().is(Items.OAK_LOG)
                    || event.getStack().is(Items.SPRUCE_LOG)
                    || event.getStack().is(Items.BIRCH_LOG)
                    || event.getStack().is(Items.JUNGLE_LOG)
                    || event.getStack().is(Items.ACACIA_LOG)
                    || event.getStack().is(Items.DARK_OAK_LOG)
                    || event.getStack().is(Items.MANGROVE_LOG)
                    || event.getStack().is(Items.CHERRY_LOG)
                    || event.getStack().is(Items.CRIMSON_STEM)
                    || event.getStack().is(Items.WARPED_STEM)) && !narrator.asAlreadyGetWood()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendWoodMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagWood();
            }

            if ((event.getStack().is(Items.COAL) || event.getStack().is(Items.CHARCOAL)) && !narrator.asAlreadyGetCoal()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendCoalMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagCoal();

            }

            if (event.getStack().is(Items.DIAMOND) && !narrator.asAlreadyGetDiamond()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendDiamondMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagDiamond();

            }

            if (event.getStack().is(Items.ENDER_PEARL) && !narrator.asAlreadyGetEnderPearl()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEnderPearlMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagEnderPearl();

            }

            if (event.getStack().is(Items.ENDER_EYE) && !narrator.asAlreadyGetEyeOfEnder()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEyeOfEnderPart1MessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> NarratorManager.isWonderlandsTalkingToPlayer(true), 10, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendWonderlandsMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.schedule(() -> NarratorManager.isWonderlandsTalkingToPlayer(false), 10, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEyeOfEnderPart2MessageToPlayerC2SPacket()), 20, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 30, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagEyeOfEnder();

            }

        });
    }

    @SubscribeEvent
    public static void onPlayerChangingDimension(PlayerEvent.PlayerChangedDimensionEvent event) {

        event.getEntity().getCapability(NarratorProvider.NARRATOR).ifPresent(narrator -> {

            if (event.getFrom().equals(Level.OVERWORLD) && event.getTo().equals(Level.NETHER) && !narrator.asAlreadyTraveledToNether()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendNetherMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagNether();

            }

            if (event.getFrom().equals(Level.OVERWORLD) && event.getTo().equals(Level.END) && !narrator.asAlreadyTraveledToEnd()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEndMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagEnd();

            }

        });

    }


    @SubscribeEvent
    public static void onPlayerSmelting(PlayerEvent.ItemSmeltedEvent event) {

        event.getEntity().getCapability(NarratorProvider.NARRATOR).ifPresent(narrator -> {

            if (event.getSmelting().is(Items.IRON_INGOT) && !narrator.asAlreadyGetIron()) {

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendSmeltingMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.schedule(() -> ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket()), 10, TimeUnit.SECONDS);

                executor.shutdown();

                narrator.setFlagIron();

            }

        });

    }

    private static boolean shouldCheckForSpawn() {
        return spawnFlag;
    }

    private static void asSpawn() {
        spawnFlag = false;
    }

    private static boolean shouldSendEmptyMessage() {
        return sendEmptyMessage;
    }

    private static void noMoreEmptyMessage() {
        sendEmptyMessage = false;
    }

    @SubscribeEvent
    public static void onPlayerSpawningForFirstTime(TickEvent.PlayerTickEvent event) {
        if (event.side == LogicalSide.SERVER) {
            event.player.getCapability(NarratorProvider.NARRATOR).ifPresent(narrator -> {

                if (!narrator.asAlreadySpawn() && shouldCheckForSpawn()) {

                    asSpawn();

                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                    executor.schedule(() -> ModMessages.sendToServer(new NarratorSendSpawnMessageToPlayerC2SPacket()), 0, TimeUnit.SECONDS);

                    executor.schedule(() -> narrator.setFlagSpawn(), 10, TimeUnit.SECONDS);

                    executor.shutdown();

                } else if (narrator.asAlreadySpawn() && shouldSendEmptyMessage()) {
                    ModMessages.sendToServer(new NarratorSendEmptyMessageToPlayerC2SPacket());
                    noMoreEmptyMessage();
                }
            });
        }
    }


    @SubscribeEvent
    public static void onWonderlandsConnectionWithPlayerForFirstTime(ViewportEvent.ComputeFov event) {
        Player player = Minecraft.getInstance().player;

        player.getCapability(NarratorProvider.NARRATOR).ifPresent(narrator -> {

            if (NarratorManager.wonderlandsTalkingToPlayer()) {
                event.setFOV(100.0F);
            } else {
                event.setFOV(70.0F);
            }

        });

    }

}
