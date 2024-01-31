package net.h8sh.playermod.screen.camera;


import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;

import javax.annotation.Nullable;
import java.util.function.IntFunction;

public enum CameraMod implements ModStringRepresentable {
    Vanilla(0, "vanilla"),
    Custom(1, "custom");
    private static final ModStringRepresentable.EnumCodec<CameraMod> CODEC = ModStringRepresentable.fromEnum(CameraMod::values);
    private static final IntFunction<CameraMod> BY_ID = ByIdMap.continuous(CameraMod::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    private final int id;
    private final String key;

    private CameraMod(int pId, String pKey) {
        this.id = pId;
        this.key = pKey;
    }

    public static CameraMod byId(int pId) {
        return BY_ID.apply(pId);
    }

    @Nullable
    public static CameraMod byName(String pName) {
        return CODEC.byName(pName);
    }

    public int getId() {
        return this.id;
    }

    public Component getDisplayName() {
        return Component.translatable(this.key);
    }

    public String getKey() {
        return this.key;
    }

    public String getSerializedName() {
        return this.key;
    }
    }