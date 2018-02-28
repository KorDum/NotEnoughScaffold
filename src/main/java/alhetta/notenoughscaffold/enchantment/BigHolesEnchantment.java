package alhetta.notenoughscaffold.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.util.IdentityUtil;

public class BigHolesEnchantment extends Enchantment {
    private Config config;

    public BigHolesEnchantment(Config config) {
        super(Rarity.VERY_RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
        });
        this.config = config;
    }

    public boolean canApply(ItemStack stack) {
        return (config.canBigHoleOnShovel && IdentityUtil.isShovel(stack))
            || (config.canBigHoleOnPickaxe && IdentityUtil.isPickaxe(stack));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return canApply(stack);
    }

    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        if (ench.getRegistryName().getResourcePath().equals("smelting")) {
            return false;
        }
        return super.canApplyTogether(ench);
    }
}
