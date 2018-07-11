package alhetta.notenoughscaffold.enchantment;

import alhetta.notenoughscaffold.config.Config;
import alhetta.notenoughscaffold.util.IdentityUtil;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class BigHolesEnchantment extends Enchantment {
    private Config config;

    public BigHolesEnchantment(Config config) {
        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
        });
        this.config = config;
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return (config.canBigHoleOnShovel && IdentityUtil.isShovel(stack))
            || (config.canBigHoleOnPickaxe && IdentityUtil.isPickaxe(stack));
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return canApply(stack);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 15;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return super.getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }
}
