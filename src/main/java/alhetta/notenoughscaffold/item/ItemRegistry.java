package alhetta.notenoughscaffold.item;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

import alhetta.notenoughscaffold.block.BlockBase;
import alhetta.notenoughscaffold.block.BlockRegistry;

public class ItemRegistry {
    public static Item WOODEN_SCAFFOLD;
    public static Item IRON_SCAFFOLD;

    private static List<Item> itemList;

    public static void init() {
        itemList = new ArrayList<>();
        WOODEN_SCAFFOLD = registerItem(BlockRegistry.WOODEN_SCAFFOLD);
        IRON_SCAFFOLD = registerItem(BlockRegistry.IRON_SCAFFOLD);
    }

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.registerAll(itemList.toArray(new Item[0]));
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        for (Item item : itemList) {
            registerRender(item);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerRender(Item item) {
        ModelResourceLocation resourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
        ModelLoader.setCustomModelResourceLocation(item, 0, resourceLocation);
    }

    private static Item registerItem(Item item, String name) {
        itemList.add(item);
        item.setUnlocalizedName(name);
        item.setRegistryName(name);
        item.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        return item;
    }

    private static Item registerItem(BlockBase block) {
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        item.setUnlocalizedName(block.getName());
        itemList.add(item);
        return item;
    }
}
