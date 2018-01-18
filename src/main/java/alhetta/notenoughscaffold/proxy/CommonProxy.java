package alhetta.notenoughscaffold.proxy;

import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.handler.BlockRegistry;
import alhetta.notenoughscaffold.handler.EnchantmentRegistry;
import alhetta.notenoughscaffold.handler.ItemRegistry;

public class CommonProxy {
    public void preInit(Config config) {
        BlockRegistry.init();
        ItemRegistry.init();
        EnchantmentRegistry.init(config);
    }
}
