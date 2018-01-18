package alhetta.notenoughscaffold.handler;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class ItemRegistry {
    private static List<Item> itemList;

    public static void init() {
        itemList = new ArrayList<>();
        registerItem(BlockRegistry.WOODEN_SCAFFOLD);
        registerItem(BlockRegistry.IRON_SCAFFOLD);
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

    private static Item registerItem(Block block) {
        ItemBlock item = new ItemBlock(block);
        item.setRegistryName(block.getRegistryName());
        item.setUnlocalizedName(block.getRegistryName().getResourcePath());
        itemList.add(item);
        return item;
    }
}
