package net.h8sh.playermod.ability.invocator.invocation;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public class InvocationCapability {

    private static boolean onInvocation;

    public static boolean getOnInvocation() {
        return InvocationCapability.onInvocation;
    }

    public static void setOnInvocation(boolean onInvocation) {
        InvocationCapability.onInvocation = onInvocation;
    }

    public void copyFrom(InvocationCapability source) {
        InvocationCapability.onInvocation = source.onInvocation;
    }

    public void savedNBTData(CompoundTag compoundTag) {
        compoundTag.putBoolean("onInvocation", onInvocation);
    }

    public void loadNBTData(CompoundTag compoundTag) {
        onInvocation = compoundTag.getBoolean("onInvocation");
    }

}