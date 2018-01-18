package alhetta.notenoughscaffold;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.proxy.CommonProxy;

@Mod(
    modid = NotEnoughScaffold.MODID,
    version = NotEnoughScaffold.VERSION
)
public class NotEnoughScaffold {
    public static final String MODID = "notenoughscaffold";
    public static final String VERSION = "1.3";

    @SidedProxy(
        clientSide = "alhetta.notenoughscaffold.proxy.ClientProxy",
        serverSide = "alhetta.notenoughscaffold.proxy.ServerProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config config = new Config(event.getSuggestedConfigurationFile());
        proxy.preInit(config);
    }
}
