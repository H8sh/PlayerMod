package net.h8sh.playermod.event;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.ability.berserk.charge.ChargeCapabilityProvider;
import net.h8sh.playermod.ability.berserk.healthsacrifice.HealthSacrificeCapabilityProvider;
import net.h8sh.playermod.ability.berserk.rage.RageCapabilityProvider;
import net.h8sh.playermod.ability.berserk.slam.SlamCapabilityProvider;
import net.h8sh.playermod.ability.druid.aquameta.grab.GrabCapabilityProvider;
import net.h8sh.playermod.ability.druid.aquameta.shield.ShieldCapabilityProvider;
import net.h8sh.playermod.ability.druid.aquameta.wateraoe.WaterAoECapabilityProvider;
import net.h8sh.playermod.ability.druid.firemeta.damage_spell.DamageSpellCapabilityProvider;
import net.h8sh.playermod.ability.druid.firemeta.fire_aura.FireAuraCapabilityProvider;
import net.h8sh.playermod.ability.druid.firemeta.firescream.FireScreamCapabilityProvider;
import net.h8sh.playermod.ability.druid.metamorphose.MetamorphoseProvider;
import net.h8sh.playermod.ability.druid.spiritusmeta.aquaboost.AquaBoostCapabilityProvider;
import net.h8sh.playermod.ability.druid.spiritusmeta.fireboost.FireBoostCapabilityProvider;
import net.h8sh.playermod.ability.druid.spiritusmeta.windboost.WindBoostCapabilityProvider;
import net.h8sh.playermod.ability.druid.windmeta.dodge.DodgeCapabilityProvider;
import net.h8sh.playermod.ability.druid.windmeta.range.RangeCapabilityProvider;
import net.h8sh.playermod.ability.druid.windmeta.speed.SpeedCapabilityProvider;
import net.h8sh.playermod.ability.gunslinger.sniper.SniperCapabilityProvider;
import net.h8sh.playermod.ability.invocator.assemble.AssembleCapabilityProvider;
import net.h8sh.playermod.ability.invocator.boost.BoostCapabilityProvider;
import net.h8sh.playermod.ability.invocator.invocation.InvocationCapabilityProvider;
import net.h8sh.playermod.ability.invocator.target.TargetCapabilityProvider;
import net.h8sh.playermod.ability.paladin.armor.ArmorCapabilityProvider;
import net.h8sh.playermod.ability.paladin.damageboost.DamageBoostCapabilityProvider;
import net.h8sh.playermod.ability.paladin.heal.HealCapabilityProvider;
import net.h8sh.playermod.ability.paladin.lightningstrike.LightningStrikeCapabilityProvider;
import net.h8sh.playermod.ability.rogue.doublee.DoubleCapabilityProvider;
import net.h8sh.playermod.ability.rogue.shot.ShotCapabilityProvider;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapabilityProvider;
import net.h8sh.playermod.ability.rogue.teleportation.TeleportationCapabilityProvider;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapabilityProvider;
import net.h8sh.playermod.ability.wizard.freeze.FreezeCapabilityProvider;
import net.h8sh.playermod.ability.wizard.laser.LaserCapabilityProvider;
import net.h8sh.playermod.ability.wizard.mana.ManaCapabilityProvider;
import net.h8sh.playermod.ability.wizard.mana.crystal.CrystalCapabilityProvider;
import net.h8sh.playermod.animation.handler.AnimationProvider;
import net.h8sh.playermod.capability.camera.CameraProvider;
import net.h8sh.playermod.capability.narrator.NarratorProvider;
import net.h8sh.playermod.capability.pet.PetProvider;
import net.h8sh.playermod.capability.profession.ProfessionProvider;
import net.h8sh.playermod.capability.reputation.ReputationProvider;
import net.h8sh.playermod.capability.riding.RidingProvider;
import net.h8sh.playermod.capability.travel.TravelProvider;
import net.h8sh.playermod.entity.ModEntities;
import net.h8sh.playermod.entity.custom.CrystalEntity;
import net.h8sh.playermod.entity.custom.LivingLamppost;
import net.h8sh.playermod.entity.custom.PnjEntity;
import net.h8sh.playermod.entity.custom.SwouiffiEntity;
import net.h8sh.playermod.world.dimension.DimensionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ModEvents {

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID)
    public static class ForgeEvents {

        @SubscribeEvent
        public static void onAttachedCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {

                if (!event.getObject().getCapability(CameraProvider.CAMERA).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "camera"), new CameraProvider());
                }
                if (!event.getObject().getCapability(AnimationProvider.ANIMATION_HANDLER).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "animation"), new AnimationProvider());
                }
                if (!event.getObject().getCapability(ProfessionProvider.PROFESSION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "profession"), new ProfessionProvider());
                }
                if (!event.getObject().getCapability(NarratorProvider.NARRATOR).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "narrator"), new NarratorProvider());
                }
                if (!event.getObject().getCapability(ReputationProvider.REPUTATION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "reputation"), new ReputationProvider());
                }
                if (!event.getObject().getCapability(RidingProvider.RIDING).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "riding"), new RidingProvider());
                }
                if (!event.getObject().getCapability(CameraProvider.CAMERA).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "questing"), new CameraProvider());
                }
                if (!event.getObject().getCapability(TravelProvider.PLAYER_TRAVEL).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "travel"), new TravelProvider());
                }
                if (!event.getObject().getCapability(DimensionProvider.PLAYER_DIMENSION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "dimension"), new DimensionProvider());
                }
                if (!event.getObject().getCapability(PetProvider.PET).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "pet"), new PetProvider());
                }

                //GUNSLINGER -------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(SniperCapabilityProvider.PLAYER_SNIPER).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "sniper"), new SniperCapabilityProvider());
                }

                //WIZARD -----------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(ManaCapabilityProvider.PLAYER_MANA).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "mana"), new ManaCapabilityProvider());
                }
                if (!event.getObject().getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "crystal"), new CrystalCapabilityProvider());
                }
                if (!event.getObject().getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "magic_aoe"), new MagicAoECapabilityProvider());
                }
                if (!event.getObject().getCapability(FreezeCapabilityProvider.PLAYER_FREEZE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "freeze"), new FreezeCapabilityProvider());
                }
                if (!event.getObject().getCapability(LaserCapabilityProvider.PLAYER_LASER).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "laser"), new LaserCapabilityProvider());
                }

                //ROGUE ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(SmokeCapabilityProvider.PLAYER_SMOKE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "smoke"), new SmokeCapabilityProvider());
                }
                if (!event.getObject().getCapability(TeleportationCapabilityProvider.PLAYER_TELEPORTATION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "teleportation"), new TeleportationCapabilityProvider());
                }
                if (!event.getObject().getCapability(ShotCapabilityProvider.PLAYER_SHOT).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "shot"), new ShotCapabilityProvider());
                }
                if (!event.getObject().getCapability(DoubleCapabilityProvider.PLAYER_DOUBLE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "double"), new DoubleCapabilityProvider());
                }

                //BERSERK ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(HealthSacrificeCapabilityProvider.PLAYER_HEALTHSACRIFICE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "health_sacrifice"), new HealthSacrificeCapabilityProvider());
                }
                if (!event.getObject().getCapability(ChargeCapabilityProvider.PLAYER_CHARGE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "charge"), new ChargeCapabilityProvider());
                }
                if (!event.getObject().getCapability(SlamCapabilityProvider.PLAYER_SLAM).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "slam"), new SlamCapabilityProvider());
                }
                if (!event.getObject().getCapability(RageCapabilityProvider.PLAYER_RAGE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "rage"), new RageCapabilityProvider());
                }

                //DRUID ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(MetamorphoseProvider.METAMORPHOSE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "metamorphose"), new MetamorphoseProvider());
                }

                //INVOCATOR ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(AssembleCapabilityProvider.PLAYER_ASSEMBLE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "assemble"), new AssembleCapabilityProvider());
                }
                if (!event.getObject().getCapability(BoostCapabilityProvider.PLAYER_BOOST).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "boost"), new BoostCapabilityProvider());
                }
                if (!event.getObject().getCapability(InvocationCapabilityProvider.PLAYER_INVOCATION).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "invocation"), new InvocationCapabilityProvider());
                }
                if (!event.getObject().getCapability(TargetCapabilityProvider.PLAYER_TARGET).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "target"), new TargetCapabilityProvider());
                }

                //PALADIN ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(ArmorCapabilityProvider.PLAYER_ARMORBUFF).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "armor_buff"), new ArmorCapabilityProvider());
                }
                if (!event.getObject().getCapability(DamageBoostCapabilityProvider.PLAYER_DAMAGEBOOST).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "damage_boost"), new DamageBoostCapabilityProvider());
                }
                if (!event.getObject().getCapability(HealCapabilityProvider.PLAYER_HEAL).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "heal"), new HealCapabilityProvider());
                }
                if (!event.getObject().getCapability(LightningStrikeCapabilityProvider.PLAYER_LIGHTNINGSTRIKE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "lightning_strike"), new LightningStrikeCapabilityProvider());
                }

                //SPIRITUS ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(FireBoostCapabilityProvider.PLAYER_FIREBOOST).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "fire_boost"), new FireBoostCapabilityProvider());
                }
                if (!event.getObject().getCapability(AquaBoostCapabilityProvider.PLAYER_AQUABOOST).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "aqua_boost"), new AquaBoostCapabilityProvider());
                }
                if (!event.getObject().getCapability(WindBoostCapabilityProvider.PLAYER_WINDBOOST).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "wind_boost"), new WindBoostCapabilityProvider());
                }

                //FIREMETA ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(DamageSpellCapabilityProvider.PLAYER_DAMAGESPELL).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "damage_spell"), new DamageSpellCapabilityProvider());
                }
                if (!event.getObject().getCapability(FireAuraCapabilityProvider.PLAYER_FIREAURA).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "fire_aura"), new FireAuraCapabilityProvider());
                }
                if (!event.getObject().getCapability(FireScreamCapabilityProvider.PLAYER_FIRESCREAM).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "fire_scream"), new FireScreamCapabilityProvider());
                }

                //AQUAMETA ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(GrabCapabilityProvider.PLAYER_GRAB).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "grab"), new GrabCapabilityProvider());
                }
                if (!event.getObject().getCapability(ShieldCapabilityProvider.PLAYER_SHIELD).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "shield"), new ShieldCapabilityProvider());
                }
                if (!event.getObject().getCapability(WaterAoECapabilityProvider.PLAYER_WATERAOE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "water_aoe"), new WaterAoECapabilityProvider());
                }

                //WINDMETA ------------------------------------------------------------------------------------------------

                if (!event.getObject().getCapability(SpeedCapabilityProvider.PLAYER_SPEED).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "speed"), new SpeedCapabilityProvider());
                }
                if (!event.getObject().getCapability(RangeCapabilityProvider.PLAYER_RANGE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "range"), new RangeCapabilityProvider());
                }
                if (!event.getObject().getCapability(DodgeCapabilityProvider.PLAYER_DODGE).isPresent()) {
                    event.addCapability(new ResourceLocation(PlayerMod.MODID, "dodge"), new DodgeCapabilityProvider());
                }

            }
        }

        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event) {
            if (event.isWasDeath()) {

                // Capability saver ------------------------------------------------------------------------------------
                event.getOriginal().reviveCaps();

                event.getOriginal().getCapability(PetProvider.PET).ifPresent(oldStore -> {
                    event.getEntity().getCapability(PetProvider.PET).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(CameraProvider.CAMERA).ifPresent(oldStore -> {
                    event.getEntity().getCapability(CameraProvider.CAMERA).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(AnimationProvider.ANIMATION_HANDLER).ifPresent(oldStore -> {
                    event.getEntity().getCapability(AnimationProvider.ANIMATION_HANDLER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(NarratorProvider.NARRATOR).ifPresent(oldStore -> {
                    event.getEntity().getCapability(NarratorProvider.NARRATOR).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(ProfessionProvider.PROFESSION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ProfessionProvider.PROFESSION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(ReputationProvider.REPUTATION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ReputationProvider.REPUTATION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(RidingProvider.RIDING).ifPresent(oldStore -> {
                    event.getEntity().getCapability(RidingProvider.RIDING).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(CameraProvider.CAMERA).ifPresent(oldStore -> {
                    event.getEntity().getCapability(CameraProvider.CAMERA).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(TravelProvider.PLAYER_TRAVEL).ifPresent(oldStore -> {
                    event.getEntity().getCapability(TravelProvider.PLAYER_TRAVEL).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(DimensionProvider.PLAYER_DIMENSION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(DimensionProvider.PLAYER_DIMENSION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //GUNSLINGER -------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(SniperCapabilityProvider.PLAYER_SNIPER).ifPresent(oldStore -> {
                    event.getEntity().getCapability(SniperCapabilityProvider.PLAYER_SNIPER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //WIZARD -----------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(ManaCapabilityProvider.PLAYER_MANA).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ManaCapabilityProvider.PLAYER_MANA).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(MagicAoECapabilityProvider.PLAYER_MAGIC_AOE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(oldStore -> {
                    event.getEntity().getCapability(CrystalCapabilityProvider.PLAYER_CRYSTAL).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(FreezeCapabilityProvider.PLAYER_FREEZE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(FreezeCapabilityProvider.PLAYER_FREEZE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(LaserCapabilityProvider.PLAYER_LASER).ifPresent(oldStore -> {
                    event.getEntity().getCapability(LaserCapabilityProvider.PLAYER_LASER).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });


                //ROGUE ------------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(SmokeCapabilityProvider.PLAYER_SMOKE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(SmokeCapabilityProvider.PLAYER_SMOKE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(DoubleCapabilityProvider.PLAYER_DOUBLE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(DoubleCapabilityProvider.PLAYER_DOUBLE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(ShotCapabilityProvider.PLAYER_SHOT).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ShotCapabilityProvider.PLAYER_SHOT).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(TeleportationCapabilityProvider.PLAYER_TELEPORTATION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(TeleportationCapabilityProvider.PLAYER_TELEPORTATION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //BERSERK ----------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(ChargeCapabilityProvider.PLAYER_CHARGE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ChargeCapabilityProvider.PLAYER_CHARGE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(HealthSacrificeCapabilityProvider.PLAYER_HEALTHSACRIFICE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(HealthSacrificeCapabilityProvider.PLAYER_HEALTHSACRIFICE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(RageCapabilityProvider.PLAYER_RAGE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(RageCapabilityProvider.PLAYER_RAGE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(SlamCapabilityProvider.PLAYER_SLAM).ifPresent(oldStore -> {
                    event.getEntity().getCapability(SlamCapabilityProvider.PLAYER_SLAM).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //INVOCATOR --------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(AssembleCapabilityProvider.PLAYER_ASSEMBLE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(AssembleCapabilityProvider.PLAYER_ASSEMBLE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(TargetCapabilityProvider.PLAYER_TARGET).ifPresent(oldStore -> {
                    event.getEntity().getCapability(TargetCapabilityProvider.PLAYER_TARGET).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(BoostCapabilityProvider.PLAYER_BOOST).ifPresent(oldStore -> {
                    event.getEntity().getCapability(BoostCapabilityProvider.PLAYER_BOOST).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(InvocationCapabilityProvider.PLAYER_INVOCATION).ifPresent(oldStore -> {
                    event.getEntity().getCapability(InvocationCapabilityProvider.PLAYER_INVOCATION).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //DRUID ------------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(MetamorphoseProvider.METAMORPHOSE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(MetamorphoseProvider.METAMORPHOSE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //PALADIN ----------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(ArmorCapabilityProvider.PLAYER_ARMORBUFF).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ArmorCapabilityProvider.PLAYER_ARMORBUFF).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(DamageBoostCapabilityProvider.PLAYER_DAMAGEBOOST).ifPresent(oldStore -> {
                    event.getEntity().getCapability(DamageBoostCapabilityProvider.PLAYER_DAMAGEBOOST).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(HealCapabilityProvider.PLAYER_HEAL).ifPresent(oldStore -> {
                    event.getEntity().getCapability(HealCapabilityProvider.PLAYER_HEAL).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(LightningStrikeCapabilityProvider.PLAYER_LIGHTNINGSTRIKE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(LightningStrikeCapabilityProvider.PLAYER_LIGHTNINGSTRIKE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //SPIRITUS ---------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(AquaBoostCapabilityProvider.PLAYER_AQUABOOST).ifPresent(oldStore -> {
                    event.getEntity().getCapability(AquaBoostCapabilityProvider.PLAYER_AQUABOOST).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(FireBoostCapabilityProvider.PLAYER_FIREBOOST).ifPresent(oldStore -> {
                    event.getEntity().getCapability(FireBoostCapabilityProvider.PLAYER_FIREBOOST).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(WindBoostCapabilityProvider.PLAYER_WINDBOOST).ifPresent(oldStore -> {
                    event.getEntity().getCapability(WindBoostCapabilityProvider.PLAYER_WINDBOOST).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //FIREMETA ---------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(DamageSpellCapabilityProvider.PLAYER_DAMAGESPELL).ifPresent(oldStore -> {
                    event.getEntity().getCapability(DamageSpellCapabilityProvider.PLAYER_DAMAGESPELL).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(FireAuraCapabilityProvider.PLAYER_FIREAURA).ifPresent(oldStore -> {
                    event.getEntity().getCapability(FireAuraCapabilityProvider.PLAYER_FIREAURA).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(FireScreamCapabilityProvider.PLAYER_FIRESCREAM).ifPresent(oldStore -> {
                    event.getEntity().getCapability(FireScreamCapabilityProvider.PLAYER_FIRESCREAM).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //AQUAMETA ---------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(GrabCapabilityProvider.PLAYER_GRAB).ifPresent(oldStore -> {
                    event.getEntity().getCapability(GrabCapabilityProvider.PLAYER_GRAB).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(ShieldCapabilityProvider.PLAYER_SHIELD).ifPresent(oldStore -> {
                    event.getEntity().getCapability(ShieldCapabilityProvider.PLAYER_SHIELD).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(WaterAoECapabilityProvider.PLAYER_WATERAOE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(WaterAoECapabilityProvider.PLAYER_WATERAOE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                //WINDMETA ---------------------------------------------------------------------------------------------

                event.getOriginal().getCapability(DodgeCapabilityProvider.PLAYER_DODGE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(DodgeCapabilityProvider.PLAYER_DODGE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(RangeCapabilityProvider.PLAYER_RANGE).ifPresent(oldStore -> {
                    event.getEntity().getCapability(RangeCapabilityProvider.PLAYER_RANGE).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(SpeedCapabilityProvider.PLAYER_SPEED).ifPresent(oldStore -> {
                    event.getEntity().getCapability(SpeedCapabilityProvider.PLAYER_SPEED).ifPresent(newStore -> {
                        newStore.copyFrom(oldStore);
                    });
                });

                event.getOriginal().invalidateCaps();
            }
        }

    }

    @Mod.EventBusSubscriber(modid = PlayerMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {

        @SubscribeEvent
        public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
            event.put(ModEntities.SWOUIFFI.get(), SwouiffiEntity.setAttributes());
            event.put(ModEntities.LIVING_LAMPPOST.get(), LivingLamppost.setAttributes());
            event.put(ModEntities.CRYSTAL.get(), CrystalEntity.setAttributes());
            event.put(ModEntities.CUSTOM_PNJ.get(), PnjEntity.setAttributes());
        }

    }

}
