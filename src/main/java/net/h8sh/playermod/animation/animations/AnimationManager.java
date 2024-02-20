package net.h8sh.playermod.animation.animations;

public class AnimationManager {

    //FLAG -------------------------------------------------------------------------------------------------------------
    public static int ANIM_ATTACK_FLAG;
    public static int ANIM_SHIFT_DOWN_FLAG;
    public static final float STEVE_FALL_FLAG = 1.5F;
    public static final float MECHANIC_FALL_FLAG = 1.5F;
    public static final float GUNSLINGER_FALL_FLAG = 1.5F;
    public static final float INVOCATOR_FALL_FLAG = 1.5F;
    public static final float ROGUE_FALL_FLAG = 1.5F;
    public static final float SPIRITUSMETA_FALL_FLAG = 1.5F;
    public static final float WINDMETA_FALL_FLAG = 1.5F;
    public static final float AQUAMETA_FALL_FLAG = 1.5F;
    public static final float DRUID_FALL_FLAG = 1.5F;
    public static final float FIREMETA_FALL_FLAG = 1.5F;
    public static final float WIZARD_FALL_FLAG = 1.5F;
    public static final float PALADIN_FALL_FLAG = 1.5F;

    //TICK COUNT STEVE -------------------------------------------------------------------------------------------------
    public static final int STEVE_BACK_DASH_ANIMATION_TICK_COUNT = 120;
    public static final int STEVE_FRONT_DASH_ANIMATION_TICK_COUNT = 77;
    public static final int STEVE_RIGHT_DASH_ANIMATION_TICK_COUNT = 80;
    public static final int STEVE_LEFT_DASH_ANIMATION_TICK_COUNT = 80;
    public static final int STEVE_JUMP_ANIMATION_TICK_COUNT = 100;
    public static final int STEVE_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int STEVE_ATTACK_ANIMATION_TICK_COUNT = 100;
    public static final int STEVE_FALL_ANIMATION_TICK_COUNT = 100;
    public static final int STEVE_IDLE_SHIFT_DOWN_TICK_COUNT = 100;
    //SPEED STEVE ------------------------------------------------------------------------------------------------------
    public static final float STEVE_BACK_DASH_ANIMATION_SPEED = 0.3F;
    public static final float STEVE_IDLE_ANIMATION_SPEED = 0.7F;
    public static final float STEVE_FRONT_DASH_ANIMATION_SPEED = 0.3F;
    public static final float STEVE_RIGHT_DASH_SPEED = 0.3F;
    public static final float STEVE_LEFT_DASH_ANIMATION_SPEED = 0.3F;
    public static final float STEVE_JUMP_ANIMATION_SPEED = 0.2F;
    public static final float STEVE_FALL_ANIMATION_SPEED = 0.2F;
    public static final float STEVE_DEATH_ANIMATION_SPEED = 0.5F;
    public static final float STEVE_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 0.7F;
    public static final float STEVE_ATTACK_ANIMATION_SPEED = 0.2F;

    // TICK COUNT MECHANIC ---------------------------------------------------------------------------------------------
    public static final int MECHANIC_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int MECHANIC_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED MECHANIC --------------------------------------------------------------------------------------------------
    public static final float MECHANIC_IDLE_ANIMATION_SPEED = 300;
    public static final float MECHANIC_DEATH_ANIMATION_SPEED = 300;
    public static final float MECHANIC_ATTACK_ANIMATION_SPEED = 300;
    public static final float MECHANIC_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float MECHANIC_JUMP_ANIMATION_SPEED = 300;
    public static final float MECHANIC_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float MECHANIC_FALL_ANIMATION_SPEED = 300;
    public static final float MECHANIC_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float MECHANIC_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float MECHANIC_RIGHT_DASH_SPEED = 300;
    // TICK COUNT GUNSLINGER -------------------------------------------------------------------------------------------
    public static final int GUNSLINGER_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int GUNSLINGER_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED GUNSLINGER ------------------------------------------------------------------------------------------------
    public static final float GUNSLINGER_IDLE_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_DEATH_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_ATTACK_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_JUMP_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_FALL_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float GUNSLINGER_RIGHT_DASH_SPEED = 300;
    // TICK COUNT INVOCATOR --------------------------------------------------------------------------------------------
    public static final int INVOCATOR_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int INVOCATOR_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED INVOCATOR -------------------------------------------------------------------------------------------------
    public static final float INVOCATOR_IDLE_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_DEATH_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_ATTACK_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_JUMP_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_FALL_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float INVOCATOR_RIGHT_DASH_SPEED = 300;
    // TICK COUNT ROGUE ------------------------------------------------------------------------------------------------
    public static final int ROGUE_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int ROGUE_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED ROGUE -----------------------------------------------------------------------------------------------------
    public static final float ROGUE_IDLE_ANIMATION_SPEED = 300;
    public static final float ROGUE_DEATH_ANIMATION_SPEED = 300;
    public static final float ROGUE_ATTACK_ANIMATION_SPEED = 300;
    public static final float ROGUE_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float ROGUE_JUMP_ANIMATION_SPEED = 300;
    public static final float ROGUE_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float ROGUE_FALL_ANIMATION_SPEED = 300;
    public static final float ROGUE_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float ROGUE_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float ROGUE_RIGHT_DASH_SPEED = 300;
    // TICK COUNT SPIRITUS ---------------------------------------------------------------------------------------------
    public static final int SPIRITUSMETA_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int SPIRITUSMETA_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED SPIRITUS --------------------------------------------------------------------------------------------------
    public static final float SPIRITUSMETA_IDLE_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_DEATH_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_ATTACK_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_JUMP_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_FALL_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float SPIRITUSMETA_RIGHT_DASH_SPEED = 300;
    // TICK COUNT WIND META --------------------------------------------------------------------------------------------
    public static final int WINDMETA_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WINDMETA_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED WIND META
    public static final float WINDMETA_IDLE_ANIMATION_SPEED = 300;
    public static final float WINDMETA_DEATH_ANIMATION_SPEED = 300;
    public static final float WINDMETA_ATTACK_ANIMATION_SPEED = 300;
    public static final float WINDMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float WINDMETA_JUMP_ANIMATION_SPEED = 300;
    public static final float WINDMETA_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float WINDMETA_FALL_ANIMATION_SPEED = 300;
    public static final float WINDMETA_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float WINDMETA_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float WINDMETA_RIGHT_DASH_SPEED = 300;
    // TICK COUNT AQUA META --------------------------------------------------------------------------------------------
    public static final int AQUAMETA_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int AQUAMETA_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED AQUA META -------------------------------------------------------------------------------------------------
    public static final float AQUAMETA_IDLE_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_DEATH_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_ATTACK_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_JUMP_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_FALL_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float AQUAMETA_RIGHT_DASH_SPEED = 300;

    // TICK COUNT DRUID ------------------------------------------------------------------------------------------------
    public static final int DRUID_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int DRUID_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED DRUID -----------------------------------------------------------------------------------------------------
    public static final float DRUID_IDLE_ANIMATION_SPEED = 300;
    public static final float DRUID_DEATH_ANIMATION_SPEED = 300;
    public static final float DRUID_ATTACK_ANIMATION_SPEED = 300;
    public static final float DRUID_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float DRUID_JUMP_ANIMATION_SPEED = 300;
    public static final float DRUID_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float DRUID_FALL_ANIMATION_SPEED = 300;
    public static final float DRUID_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float DRUID_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float DRUID_RIGHT_DASH_SPEED = 300;
    // TICK COUNT FIRE META --------------------------------------------------------------------------------------------
    public static final int FIREMETA_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int FIREMETA_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED FIRE META -------------------------------------------------------------------------------------------------
    public static final float FIREMETA_IDLE_ANIMATION_SPEED = 300;
    public static final float FIREMETA_DEATH_ANIMATION_SPEED = 300;
    public static final float FIREMETA_ATTACK_ANIMATION_SPEED = 300;
    public static final float FIREMETA_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float FIREMETA_JUMP_ANIMATION_SPEED = 300;
    public static final float FIREMETA_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float FIREMETA_FALL_ANIMATION_SPEED = 300;
    public static final float FIREMETA_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float FIREMETA_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float FIREMETA_RIGHT_DASH_SPEED = 300;
    // TICK COUNT WIZARD -----------------------------------------------------------------------------------------------
    public static final int WIZARD_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_ATTACK_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int WIZARD_IDLE_SHIFT_DOWN_TICK_COUNT = 200;
    public static final int PALADIN_BACK_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_ATTACK_ANIMATION_TICK_COUNT = 200;

    // SPEED WIZARD ----------------------------------------------------------------------------------------------------
    public static final float WIZARD_ATTACK_ANIMATION_SPEED = 300;
    public static final float WIZARD_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float WIZARD_JUMP_ANIMATION_SPEED = 300;
    public static final float WIZARD_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float WIZARD_FALL_ANIMATION_SPEED = 300;
    public static final float WIZARD_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float WIZARD_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float WIZARD_RIGHT_DASH_SPEED = 300;
    public static final float WIZARD_IDLE_ANIMATION_SPEED = 300;
    public static final float WIZARD_DEATH_ANIMATION_SPEED = 300;
    // TICK COUNT PALADIN ----------------------------------------------------------------------------------------------
    public static final int PALADIN_DEATH_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_FALL_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_JUMP_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_FRONT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_RIGHT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_LEFT_DASH_ANIMATION_TICK_COUNT = 200;
    public static final int PALADIN_IDLE_SHIFT_DOWN_TICK_COUNT = 200;

    // SPEED PALADIN ---------------------------------------------------------------------------------------------------
    public static final float PALADIN_IDLE_ANIMATION_SPEED = 300;
    public static final float PALADIN_DEATH_ANIMATION_SPEED = 300;
    public static final float PALADIN_ATTACK_ANIMATION_SPEED = 300;
    public static final float PALADIN_IDLE_SHIFT_DOWN_ANIMATION_SPEED = 300;
    public static final float PALADIN_JUMP_ANIMATION_SPEED = 300;
    public static final float PALADIN_BACK_DASH_ANIMATION_SPEED = 300;
    public static final float PALADIN_FALL_ANIMATION_SPEED = 300;
    public static final float PALADIN_FRONT_DASH_ANIMATION_SPEED = 300;
    public static final float PALADIN_LEFT_DASH_ANIMATION_SPEED = 300;
    public static final float PALADIN_RIGHT_DASH_SPEED = 300;


}
