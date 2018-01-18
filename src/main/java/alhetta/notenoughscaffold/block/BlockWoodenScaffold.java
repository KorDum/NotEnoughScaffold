package alhetta.notenoughscaffold.block;

import net.minecraft.block.material.Material;

public class BlockWoodenScaffold extends BaseBlockScaffold {
    public BlockWoodenScaffold() {
        super(Material.WOOD, 1, 1);
        setHarvestLevel("axe", 0);
    }

    @Override
    protected double getMotionSpeed() {
        return 0.2D;
    }

    @Override
    protected int getSideBlockAvailable() {
        return 3;
    }
}
