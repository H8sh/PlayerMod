package net.h8sh.playermod.ability;

import net.h8sh.playermod.ability.berserk.charge.ChargeCapability;
import net.h8sh.playermod.ability.berserk.rage.RageCapability;
import net.h8sh.playermod.ability.druid.firemeta.damage_spell.DamageSpellCapability;
import net.h8sh.playermod.ability.druid.firemeta.fire_aura.FireAuraCapability;
import net.h8sh.playermod.ability.rogue.doublee.DoubleCapability;
import net.h8sh.playermod.ability.rogue.smoke.SmokeCapability;
import net.h8sh.playermod.ability.rogue.teleportation.TeleportationCapability;
import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.ability.wizard.laser.LaserCapability;
import net.h8sh.playermod.networking.ModMessages;
import net.h8sh.playermod.networking.classes.berserk.charge.ChargeC2SPacket;
import net.h8sh.playermod.networking.classes.berserk.rage.RageC2SPacket;
import net.h8sh.playermod.networking.classes.berserk.slam.SlamC2SPacket;
import net.h8sh.playermod.networking.classes.druid.firemeta.damagespell.DamageSpellC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.smoke.SmokeC2SPacket;
import net.h8sh.playermod.networking.classes.rogue.teleportation.TeleportationC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.laser.LaserC2SPacket;
import net.h8sh.playermod.networking.classes.wizard.mana.ManaExtractionC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class SpellManager {

    public static void activeSpell(String spell, Minecraft minecraft) {
        Player player = minecraft.player;
        switch (spell) {
            //BASIC: no spells

            case "":
                break;

            //PALADIN: -------------------------------------------------------------------------------------------------

            case "heal":
                //TODO
                break;

            case "armor":
                //TODO
                break;

            case "damage_boost":
                //TODO
                break;

            case "lightning_strike":
                //TODO
                break;

            //WIZARD: --------------------------------------------------------------------------------------------------

            case "mana_drain":
                MagicAoECapability.setOnCD(false);
                ModMessages.sendToServer(new ManaExtractionC2SPacket());
                break;

            case "aoe":
                MagicAoECapability.setPrepareAoe(true);

                break;

            case "freeze":
                break;

            case "laser":
                LaserCapability.setOnLaserActivated(false);
                ModMessages.sendToServer(new LaserC2SPacket());
                break;

            //DRUID: ---------------------------------------------------------------------------------------------------

            case "fireMeta":
                //TODO
                break;

            case "aquaMeta":
                //TODO
                break;

            case "windMeta":
                //TODO
                break;

            case "spiritusMeta":
                //TODO
                break;

            //FIREMETA: ------------------------------------------------------------------------------------------------

            case "damage_spell":
                ModMessages.sendToServer(new DamageSpellC2SPacket());
                break;

            case "fire_aura":
                FireAuraCapability.setOnFireAura(false);
                break;

            case "fire_scream":
                DamageSpellCapability.setOnDamageSpell(false);
                break;

            //AQUAMETA: ------------------------------------------------------------------------------------------------

            case "grab":
                //TODO
                break;

            case "shield":
                //TODO
                break;

            case "waterAoe":
                //TODO
                break;

            //WINDMETA: ------------------------------------------------------------------------------------------------

            case "speed":
                //TODO
                break;

            case "range":
                //TODO
                break;

            case "dodge":
                //TODO
                break;

            //SPIRITUSMETA: --------------------------------------------------------------------------------------------

            case "fire_boost":
                //TODO
                break;

            case "aqua_boost":
                //TODO
                break;

            case "wind_boost":
                //TODO
                break;

            //ROGUE: ---------------------------------------------------------------------------------------------------

            case "smoke":
                DoubleCapability.setOnCD(false);
                ModMessages.sendToServer(new SmokeC2SPacket());
                break;

            case "teleportation":
                ModMessages.sendToServer(new TeleportationC2SPacket());
                break;

            case "shot":
                SmokeCapability.setOnSmoke(false);
                break;

            case "double":

                DoubleCapability.setPrepareTargetMark(true);
                break;

            //BERSERK: -------------------------------------------------------------------------------------------------

            case "healthSacrifice":
                break;

            case "charge":
                ModMessages.sendToServer(new ChargeC2SPacket());
                break; 

            case "slam":
                ModMessages.sendToServer(new SlamC2SPacket());
                break;

            case "rage":
                if (!RageCapability.getOnRage()) {
                    ModMessages.sendToServer(new RageC2SPacket());
                } else {
                    RageCapability.resetRage();
                }

                break;

            //INVOCATOR: -----------------------------------------------------------------------------------------------

            case "target":
                //TODO
                break;

            case "invocation":
                //TODO
                break;

            case "boost":
                //TODO
                break;

            case "assemble":
                //TODO
                break;

        }
    }

}
