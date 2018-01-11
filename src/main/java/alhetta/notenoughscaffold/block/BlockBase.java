package alhetta.notenoughscaffold.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {
    private String name;

    public BlockBase(Material material, String name, float hardness, float resistance) {
        super(material);
        this.name = name;
        setHardness(hardness);
        setResistance(resistance);
    }

    public String getName() {
        return name;
    }
}
