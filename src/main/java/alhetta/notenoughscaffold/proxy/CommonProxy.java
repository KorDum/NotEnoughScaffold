package alhetta.notenoughscaffold.proxy;

import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.registry.BlockRegistry;
import alhetta.notenoughscaffold.registry.EnchantmentRegistry;
import alhetta.notenoughscaffold.registry.ItemRegistry;

public class CommonProxy {
    public void preInit(Config config) {
        BlockRegistry.init();
        ItemRegistry.init();
        EnchantmentRegistry.init(config);
    }
}
