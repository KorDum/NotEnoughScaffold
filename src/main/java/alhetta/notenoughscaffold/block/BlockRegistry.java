package alhetta.notenoughscaffold.block;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class BlockRegistry {
    public static BlockBase WOODEN_SCAFFOLD;
    public static BlockBase IRON_SCAFFOLD;

    private static List<Block> blockList;

    public static void init() {
        blockList = new ArrayList<>();
        WOODEN_SCAFFOLD = registerBlock(new BlockWoodenScaffold("wooden_scaffold"));
        IRON_SCAFFOLD = registerBlock(new BlockIronScaffold("iron_scaffold"));
    }

    public static void registerRenders() {

    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.registerAll(blockList.toArray(new Block[0]));
    }

    private static BlockBase registerBlock(BlockBase block) {
        blockList.add(block);
        block.setUnlocalizedName(block.getName());
        block.setRegistryName(block.getName());
        block.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        return block;
    }
}
