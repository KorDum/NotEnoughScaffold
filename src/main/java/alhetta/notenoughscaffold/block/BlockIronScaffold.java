package alhetta.notenoughscaffold.block;

import net.minecraft.block.material.Material;

public class BlockIronScaffold extends BaseBlockScaffold {
    public BlockIronScaffold(String name) {
        super(Material.IRON, name, 0.8F, 2000);
        setHarvestLevel("pickaxe", 1);
    }

    @Override
    protected double getMotionSpeed() {
        return 0.3D;
    }
}
