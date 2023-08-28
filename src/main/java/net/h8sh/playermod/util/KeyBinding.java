package net.h8sh.playermod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_PROFESSION = "key.category.playermod.profession";
    public static final String KEY_CATEGORY_UTILS = "key.category.playermod.utils";
    public static final String KEY_SPELL_1 = "key.playermod.spell_1";
    public static final String KEY_SPELL_2 = "key.playermod.spell_2";
    public static final String KEY_SPELL_3 = "key.playermod.spell_3";
    public static final String KEY_ULTIMATE = "key.playermod.ultimate";
    public static final String KEY_INTERACTION = "key.playermod.interaction";
    public static final String KEY_RIDING = "key.playermod.riding";
    public static final String KEY_INVENTORY_SWITCH = "key.playermod.inventory_switch";
    public static final String KEY_SKILL_SCREEN = "key.playermod.skill_screen";
    public static final String KEY_SHOW_KEYS = "key.playermod.show_keys";

    public static final KeyMapping FIRST_SPELL_KEY = new KeyMapping(KEY_SPELL_1,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping SECOND_SPELL_KEY = new KeyMapping(KEY_SPELL_2,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_2, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping THIRD_SPELL_KEY = new KeyMapping(KEY_SPELL_3,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_3, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping ULTIMATE_SPELL_KEY = new KeyMapping(KEY_ULTIMATE,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_4, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping INTERACTION_KEY = new KeyMapping(KEY_INTERACTION,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F, KEY_CATEGORY_UTILS);

    public static final KeyMapping RIDING_KEY = new KeyMapping(KEY_RIDING,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, KEY_CATEGORY_UTILS);

    public static final KeyMapping INVENTORY_SWITCH_KEY = new KeyMapping(KEY_INVENTORY_SWITCH,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_TAB, KEY_CATEGORY_UTILS);

    public static final KeyMapping SKILL_SCREEN_KEY = new KeyMapping(KEY_SKILL_SCREEN,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_UTILS);
    public static final KeyMapping SHOW_KEYS_KEY = new KeyMapping(KEY_SHOW_KEYS,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORY_UTILS);

}
