package alhetta.notenoughscaffold.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

import alhetta.notenoughscaffold.util.IdentityUtil;

public class BigHolesEnchantment extends Enchantment {

    public BigHolesEnchantment() {
        super(Rarity.RARE, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{
            EntityEquipmentSlot.MAINHAND
        });
    }

    public boolean canApply(ItemStack stack) {
        return IdentityUtil.isShovel(stack);
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
}
