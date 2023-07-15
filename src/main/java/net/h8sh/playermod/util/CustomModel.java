package net.h8sh.playermod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.animal.Cow;

public class CustomModel {

    public static final Cow cow = EntityType.COW.create(Minecraft.getInstance().level);

}
