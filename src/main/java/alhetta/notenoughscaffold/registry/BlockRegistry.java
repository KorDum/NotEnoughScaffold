package alhetta.notenoughscaffold.registry;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import alhetta.notenoughscaffold.block.BlockIronScaffold;
import alhetta.notenoughscaffold.block.BlockWoodenScaffold;

public class BlockRegistry {
    public static Block WOODEN_SCAFFOLD;
    public static Block IRON_SCAFFOLD;

    private static List<Block> blockList;

    public static void init() {
        blockList = new ArrayList<>();
        WOODEN_SCAFFOLD = registerBlock(new BlockWoodenScaffold(), "wooden_scaffold");
        IRON_SCAFFOLD = registerBlock(new BlockIronScaffold(), "iron_scaffold");
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.registerAll(blockList.toArray(new Block[0]));
    }

    private static Block registerBlock(Block block, String name) {
        blockList.add(block);
        block.setUnlocalizedName(name);
        block.setRegistryName(name);
        block.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        return block;
    }
}
