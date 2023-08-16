package net.h8sh.playermod.hero;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import org.slf4j.Logger;

public class CustomArmorTrim {
    public static final Codec<CustomArmorTrim> CODEC = RecordCodecBuilder.create((p_267058_) -> {
        return p_267058_.group(TrimMaterial.CODEC.fieldOf("material").forGetter(CustomArmorTrim::material), TrimPattern.CODEC.fieldOf("pattern").forGetter(CustomArmorTrim::pattern)).apply(p_267058_, CustomArmorTrim::new);
    });
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String TAG_TRIM_ID = "Trim";
    private static final Component UPGRADE_TITLE = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation("smithing_template.upgrade"))).withStyle(ChatFormatting.GRAY);
    private final Holder<TrimMaterial> material;
    private final Holder<TrimPattern> pattern;
    private final Function<CustomArmorMaterial, ResourceLocation> innerTexture;
    private final Function<CustomArmorMaterial, ResourceLocation> outerTexture;

    public CustomArmorTrim(Holder<TrimMaterial> p_267249_, Holder<TrimPattern> p_267212_) {
        this.material = p_267249_;
        this.pattern = p_267212_;
        this.innerTexture = Util.memoize((p_267934_) -> {
            ResourceLocation resourcelocation = p_267212_.value().assetId();
            String s = this.getColorPaletteSuffix(p_267934_);
            return resourcelocation.withPath((p_266737_) -> {
                return "trims/models/armor/" + p_266737_ + "_leggings_" + s;
            });
        });
        this.outerTexture = Util.memoize((p_267932_) -> {
            ResourceLocation resourcelocation = p_267212_.value().assetId();
            String s = this.getColorPaletteSuffix(p_267932_);
            return resourcelocation.withPath((p_266864_) -> {
                return "trims/models/armor/" + p_266864_ + "_" + s;
            });
        });
    }

    private String getColorPaletteSuffix(CustomArmorMaterial pArmorMaterial) {
        Map<ArmorMaterials, String> map = this.material.value().overrideArmorMaterials();
        return pArmorMaterial instanceof CustomArmorMaterials && map.containsKey(pArmorMaterial) ? map.get(pArmorMaterial) : this.material.value().assetName();
    }

    public boolean hasPatternAndMaterial(Holder<TrimPattern> pPattern, Holder<TrimMaterial> pMaterial) {
        return pPattern == this.pattern && pMaterial == this.material;
    }

    public Holder<TrimPattern> pattern() {
        return this.pattern;
    }

    public Holder<TrimMaterial> material() {
        return this.material;
    }

    public ResourceLocation innerTexture(CustomArmorMaterial pArmorMaterial) {
        return this.innerTexture.apply(pArmorMaterial);
    }

    public ResourceLocation outerTexture(CustomArmorMaterial pArmorMaterial) {
        return this.outerTexture.apply(pArmorMaterial);
    }

    public boolean equals(Object pOther) {
        if (!(pOther instanceof CustomArmorTrim armortrim)) {
            return false;
        } else {
            return armortrim.pattern == this.pattern && armortrim.material == this.material;
        }
    }

    public static boolean setTrim(RegistryAccess pRegistryAccess, ItemStack pArmor, CustomArmorTrim pTrim) {
        if (pArmor.is(ItemTags.TRIMMABLE_ARMOR)) {
            pArmor.getOrCreateTag().put("Trim", CODEC.encodeStart(RegistryOps.create(NbtOps.INSTANCE, pRegistryAccess), pTrim).result().orElseThrow());
            return true;
        } else {
            return false;
        }
    }

    public static Optional<CustomArmorTrim> getTrim(RegistryAccess pRegistryAccess, ItemStack pArmor) {
        if (pArmor.is(ItemTags.TRIMMABLE_ARMOR) && pArmor.getTag() != null && pArmor.getTag().contains("Trim")) {
            CompoundTag compoundtag = pArmor.getTagElement("Trim");
            CustomArmorTrim armortrim = CODEC.parse(RegistryOps.create(NbtOps.INSTANCE, pRegistryAccess), compoundtag).resultOrPartial(LOGGER::error).orElse((CustomArmorTrim)null);
            return Optional.ofNullable(armortrim);
        } else {
            return Optional.empty();
        }
    }

    public static void appendUpgradeHoverText(ItemStack pArmor, RegistryAccess pRegistryAccess, List<Component> pTooltip) {
        Optional<CustomArmorTrim> optional = getTrim(pRegistryAccess, pArmor);
        if (optional.isPresent()) {
            CustomArmorTrim armortrim = optional.get();
            pTooltip.add(UPGRADE_TITLE);
            pTooltip.add(CommonComponents.space().append(armortrim.pattern().value().copyWithStyle(armortrim.material())));
            pTooltip.add(CommonComponents.space().append(armortrim.material().value().description()));
        }

    }
}
