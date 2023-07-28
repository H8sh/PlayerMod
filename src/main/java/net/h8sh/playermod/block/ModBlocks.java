package net.h8sh.playermod.block;

import net.h8sh.playermod.PlayerMod;
import net.h8sh.playermod.block.custom.PaladinLectern;
import net.h8sh.playermod.block.custom.PortalBlock;
import net.h8sh.playermod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, PlayerMod.MODID);



    public static final RegistryObject<Block> PORTAL_BLOCK = registerBlock("portal",
            ()-> new PortalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.ANVIL)));

    public static final RegistryObject<Block> PALADIN_LECTERN = BLOCKS.register("paladin_lectern",
            ()-> new PaladinLectern(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.ANVIL).noOcclusion()));





    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }

}
