package alhetta.notenoughscaffold.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config extends Configuration {
    private static final String ENCANTEMENT_CATEGORY = "enchantements";
    private static final String BIG_HOLE_ENCANTEMENT_PARAM = "bigHole";

    public boolean bigHole;

    public Config(File file) {
        super(file);
        bigHole = getBoolean(BIG_HOLE_ENCANTEMENT_PARAM, ENCANTEMENT_CATEGORY, true, "Allow use encantement Big Holes");
        save();
    }
}
