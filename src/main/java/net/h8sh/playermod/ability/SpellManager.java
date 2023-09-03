package net.h8sh.playermod.ability;

import net.h8sh.playermod.ability.wizard.aoe.MagicAoECapability;
import net.h8sh.playermod.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.effect.MobEffectInstance;
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
                //TODO
                break;

            case "aoe":

                MagicAoECapability.setPrepareAoe(true);

                break;

            case "freeze":
                //TODO
                break;

            case "laser":
                //TODO
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
                //TODO
                break;

            case "fire_aura":
                //TODO
                break;

            case "fire_scream":
                //TODO
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

            case "smog":
                player.addEffect(new MobEffectInstance(ModEffects.SMOG.get(), 20, 0, false, true, false));
                break;

            case "teleportation":
                //TODO
                break;

            case "shot":
                //TODO
                break;

            case "double":
                //TODO
                break;

            //BERSERK: -------------------------------------------------------------------------------------------------

            case "healthSacrifice":
                //TODO
                break;

            case "charge":
                //TODO
                break;

            case "slam":
                //TODO
                break;

            case "rage":
                //TODO
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
