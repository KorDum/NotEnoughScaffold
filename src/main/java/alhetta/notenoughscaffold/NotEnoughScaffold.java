package alhetta.notenoughscaffold;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import alhetta.notenoughscaffold.block.BlockRegistry;
import alhetta.notenoughscaffold.enchantment.EnchantmentRegistry;
import alhetta.notenoughscaffold.item.ItemRegistry;
import alhetta.notenoughscaffold.proxy.CommonProxy;

@Mod(modid = NotEnoughScaffold.MODID, version = NotEnoughScaffold.VERSION)
public class NotEnoughScaffold {
    public static final String MODID = "notenoughscaffold";
    public static final String VERSION = "1.0";

    @SidedProxy(
        clientSide = "alhetta.notenoughscaffold.proxy.ClientProxy",
        serverSide = "alhetta.notenoughscaffold.proxy.ServerProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            BlockRegistry.registerBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ItemRegistry.registerItems(event.getRegistry());
        }

        @SubscribeEvent
        @SideOnly(Side.CLIENT)
        public static void registerModels(ModelRegistryEvent event) {
            BlockRegistry.registerRenders();
            ItemRegistry.registerRenders();
        }

        @SubscribeEvent
        public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
            EnchantmentRegistry.register(event.getRegistry());
        }
    }
}
