package alhetta.notenoughscaffold.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config extends Configuration {
    private static final String ENCANTEMENT_CATEGORY = "enchantements";
    private static final String BIG_HOLE_ENCANTEMENT_PARAM = "bigHole";
    private static final String CAN_BIG_HOLE_ON_PICKAXE_PARAM = "canBigHoleOnPickaxe";
    private static final String CAN_BIG_HOLE_ON_SHOVEL_PARAM = "canBigHoleOnShovel";

    public boolean bigHole;
    public boolean canBigHoleOnPickaxe;
    public boolean canBigHoleOnShovel;

    public Config(File file) {
        super(file);
        bigHole = getBoolean(BIG_HOLE_ENCANTEMENT_PARAM, ENCANTEMENT_CATEGORY, true, "Allow use encantement Big Holes");
        canBigHoleOnPickaxe = getBoolean(CAN_BIG_HOLE_ON_PICKAXE_PARAM, ENCANTEMENT_CATEGORY, true, "Allow use encantement Big Holes on pickaxe");
        canBigHoleOnShovel = getBoolean(CAN_BIG_HOLE_ON_SHOVEL_PARAM, ENCANTEMENT_CATEGORY, true, "Allow use encantement Big Holes on shovels");
        save();
    }
}
