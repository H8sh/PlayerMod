package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.druid.metamorphose.Metamorphose;
import net.h8sh.playermod.ability.druid.metamorphose.MetamorphoseProvider;
import net.h8sh.playermod.capability.profession.Profession;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.capability.riding.Riding;
import net.h8sh.playermod.capability.riding.RidingProvider;
import net.h8sh.playermod.gui.*;
import net.h8sh.playermod.item.ModItems;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.profession.*;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PlayerMod.MODID)
public class ProfessionEvent {

    @SubscribeEvent
    public static void onPlayerLoggedIn(EntityJoinLevelEvent event) {
        var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();
        if (event.getEntity() instanceof Player player) {
            if (currentProfession == Profession.Professions.BASIC) {
                player.getCapability(ProfessionProvider.PROFESSION).ifPresent(Profession::resetProfession);
                player.getCapability(RidingProvider.RIDING).ifPresent(Riding::resetRiding);
            }
            if (currentProfession != Profession.Professions.DRUID) {
                player.getCapability(MetamorphoseProvider.METAMORPHOSE).ifPresent(Metamorphose::resetMetamorphose);
            }
            if (Profession.getProfession() != Profession.Professions.BASIC) {
                Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerGettingProfessionBook(PlayerEvent.ItemPickupEvent event) {
        Player player = event.getEntity();

        if (event.getStack().is(ModItems.PALADIN_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.PALADIN) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionPaladinC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.WIZARD_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.WIZARD) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionWizardC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.DRUID_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.DRUID) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionDruidC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.BASIC_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.BASIC) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionBasicC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.FIRST_PERSON);
                }
            });
        }
        if (event.getStack().is(ModItems.ROGUE_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.ROGUE) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionRogueC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.BERSERK_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.BERSERK) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionBerserkC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.INVOCATOR_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.INVOCATOR) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionInvocatorC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.FIREMETA_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.FIREMETA) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionFireMetaC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.AQUAMETA_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.AQUAMETA) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionAquaMetaC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.WINDMETA_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.WINDMETA) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionWindMetaC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
        if (event.getStack().is(ModItems.SPIRITUSMETA_BOOK.get())) {
            player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
                if (profession.getProfession() != Profession.Professions.SPIRITUSMETA) {
                    profession.resetProfession();
                    ModMessages.sendToServer(new ProfessionSpiritusMetaC2SPacket());
                    Minecraft.getInstance().options.setCameraType(CameraType.THIRD_PERSON_BACK);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onGuiRender(RenderGuiOverlayEvent event) {
        Player player = Minecraft.getInstance().player;
        player.getCapability(ProfessionProvider.PROFESSION).ifPresent(profession -> {
            var currentProfession = Profession.getProfession() == null ? Profession.Professions.BASIC : Profession.getProfession();


            switch (currentProfession) {
                case PALADIN:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case BERSERK:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case INVOCATOR:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case FIREMETA:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case AQUAMETA:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case WINDMETA:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case SPIRITUSMETA:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case ROGUE:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case DRUID:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    } else if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    } else if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;
                case WIZARD:
                    renderHotBarOrSpellBar(event);
                    renderSelectiveBars(event);

                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    break;
                case BASIC:
                    if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay().overlay() == ReputationOverlay.HUD_REPUTATION) {
                        event.setCanceled(true);
                    }

                    if (event.getOverlay() == VanillaGuiOverlay.HOTBAR.type()) {
                        event.getOverlay().overlay().render(
                                new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                                event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
                    }
                    if (event.getOverlay().overlay() == SpellBarOverlay.HUD_SPELL_BAR) {
                        event.setCanceled(true);
                    }
                    if (event.getOverlay().overlay() == ArrowOverlay.HUD_ARROW) {
                        event.setCanceled(true);
                    }


                    //Wizard HUD
                    if (event.getOverlay().overlay() == CrystalOverlay.HUD_CRYSTAL) {
                        event.setCanceled(true);
                    } else if (event.getOverlay().overlay() == ManaBarOverlay.HUD_MANA_BAR) {
                        event.setCanceled(true);
                    } else if (event.getOverlay().overlay() == ManaOverlay.HUD_MANA) {
                        event.setCanceled(true);
                    }
                    break;

            }
        });
    }

    public static void renderHotBarOrSpellBar(RenderGuiOverlayEvent event) {
        boolean shouldRenderHotBar = ClientEvents.getHotBar();
        if (event.getOverlay() == VanillaGuiOverlay.HOTBAR.type()) {
            if (shouldRenderHotBar) {
                event.getOverlay().overlay().render(
                        new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                        event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
            } else {
                event.setCanceled(true);
            }
        }
        if (event.getOverlay().overlay() == SpellBarOverlay.HUD_SPELL_BAR) {
            if (!shouldRenderHotBar) {
                event.getOverlay().overlay().render(
                        new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                        event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
            } else {
                event.setCanceled(true);
            }
        }
    }

    public static void renderSelectiveBars(RenderGuiOverlayEvent event) {
        if (event.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type()) {
            event.setCanceled(true);
        }
        if (event.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type()) {
            event.setCanceled(true);
        }
        if (event.getOverlay().overlay() == ReputationOverlay.HUD_REPUTATION) {
            event.getOverlay().overlay().render(
                    new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                    event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
        }
        if (event.getOverlay().overlay() == ArrowOverlay.HUD_ARROW) {
            event.getOverlay().overlay().render(
                    new ForgeGui(Minecraft.getInstance()), event.getGuiGraphics(), event.getPartialTick(),
                    event.getGuiGraphics().guiWidth(), event.getGuiGraphics().guiHeight());
        }
    }
}
