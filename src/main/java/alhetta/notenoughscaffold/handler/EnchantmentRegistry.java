package alhetta.notenoughscaffold.handler;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.IForgeRegistry;

import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.enchantment.BigHolesEnchantment;

public class EnchantmentRegistry {
    public static Enchantment BIG_HOLES;

    private static Config config;

    public static void init(Config config) {
        EnchantmentRegistry.config = config;
        if (config.bigHole) {
            BIG_HOLES = registerEnchantment(new BigHolesEnchantment(), "big_holes");
            MinecraftForge.EVENT_BUS.register(EnchantmentEventHandler.INSTANCE);
        }
    }

    public static void register(IForgeRegistry<Enchantment> registry) {
        if (config.bigHole) {
            registry.register(BIG_HOLES);
        }
    }

    private static Enchantment registerEnchantment(Enchantment enchantment, String name) {
        enchantment.setRegistryName(name);
        enchantment.setName(name);
        return enchantment;
    }
}
