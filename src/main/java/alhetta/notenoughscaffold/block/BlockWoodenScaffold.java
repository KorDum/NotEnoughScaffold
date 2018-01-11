package alhetta.notenoughscaffold.block;

import net.minecraft.block.material.Material;

public class BlockWoodenScaffold extends BaseBlockScaffold {
    public BlockWoodenScaffold(String name) {
        super(Material.WOOD, name, 1, 1);
        setHarvestLevel("axe", 0);
    }

    @Override
    protected double getMotionSpeed() {
        return 0.2D;
    }
}
