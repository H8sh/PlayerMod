package net.h8sh.playermod.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY_PROFESSION = "key.category.playermod.profession";
    public static final String KEY_DRUID = "key.playermod.druid";
    public static final String KEY_PALADIN = "key.playermod.paladin";
    public static final String KEY_WIZARD = "key.playermod.wizard";
    public static final String KEY_BASIC = "key.playermod.basic";
    public static final String KEY_CURRENT = "key.playermod.current";

    public static final KeyMapping DRUID_PROFESSION_KEY = new KeyMapping(KEY_DRUID,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping PALADIN_PROFESSION_KEY = new KeyMapping(KEY_PALADIN,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_I, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping WIZARD_PROFESSION_KEY = new KeyMapping(KEY_WIZARD,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping BASIC_PROFESSION_KEY = new KeyMapping(KEY_BASIC,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Y, KEY_CATEGORY_PROFESSION);

    public static final KeyMapping CURRENT_PROFESSION_KEY = new KeyMapping(KEY_CURRENT,
            KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, KEY_CATEGORY_PROFESSION);



}
