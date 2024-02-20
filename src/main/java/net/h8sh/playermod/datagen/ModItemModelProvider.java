package net.h8sh.playermod.datagen;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PlayerMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.PALADIN_BOOK);
        simpleItem(ModItems.WIZARD_BOOK);
        simpleItem(ModItems.DRUID_BOOK);
        simpleItem(ModItems.BASIC_BOOK);
        simpleItem(ModItems.BERSERK_BOOK);
        simpleItem(ModItems.INVOCATOR_BOOK);
        simpleItem(ModItems.FIREMETA_BOOK);
        simpleItem(ModItems.AQUAMETA_BOOK);
        simpleItem(ModItems.WINDMETA_BOOK);
        simpleItem(ModItems.SPIRITUSMETA_BOOK);
        simpleItem(ModItems.ROGUE_BOOK);
        simpleItem(ModItems.SNIPER_ITEM);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(PlayerMod.MODID,"item/" + item.getId().getPath()));
    }
}