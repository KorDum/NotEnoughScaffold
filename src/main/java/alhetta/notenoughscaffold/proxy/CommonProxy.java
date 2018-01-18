package alhetta.notenoughscaffold.proxy;

import alhetta.notenoughscaffold.block.BlockRegistry;
import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.enchantment.EnchantmentRegistry;
import alhetta.notenoughscaffold.item.ItemRegistry;

public class CommonProxy {
    public void preInit(Config config) {
        BlockRegistry.init();
        ItemRegistry.init();
        EnchantmentRegistry.init(config);
    }
}
