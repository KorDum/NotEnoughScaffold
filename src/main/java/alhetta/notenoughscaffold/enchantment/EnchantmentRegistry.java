package alhetta.notenoughscaffold.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.registries.IForgeRegistry;

public class EnchantmentRegistry {
    public static Enchantment BIG_HOLES;
    
    public static void init() {
        BIG_HOLES = registerEnchantment(new BigHolesEnchantment(), "big_holes");
        MinecraftForge.EVENT_BUS.register(EnchantmentEventHandler.INSTANCE);
    }

    public static void register(IForgeRegistry<Enchantment> registry) {
        registry.register(BIG_HOLES);
    }

    private static Enchantment registerEnchantment(Enchantment enchantment, String name) {
        enchantment.setRegistryName(name);
        enchantment.setName(name);
        return enchantment;
    }
}
