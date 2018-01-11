package alhetta.notenoughscaffold.proxy;

import alhetta.notenoughscaffold.block.BlockRegistry;
import alhetta.notenoughscaffold.enchantment.EnchantmentRegistry;
import alhetta.notenoughscaffold.item.ItemRegistry;

public class CommonProxy {
    public void preInit() {
        BlockRegistry.init();
        ItemRegistry.init();
        EnchantmentRegistry.init();
    }
}
