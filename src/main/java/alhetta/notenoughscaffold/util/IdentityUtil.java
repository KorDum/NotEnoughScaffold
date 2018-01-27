package alhetta.notenoughscaffold.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import java.util.Set;

public class IdentityUtil {
    public static boolean isShovel(ItemStack stack) {
        return isTool(stack, "shovel");
    }

    public static boolean isPickaxe(ItemStack stack) {
        return isTool(stack, "pickaxe");
    }

    private static boolean isTool(ItemStack stack, String type) {
        if (!(stack.getItem() instanceof ItemTool)) {
            return false;
        }

        ItemTool item = (ItemTool) stack.getItem();
        Set<String> toolClasses = item.getToolClasses(stack);
        for (String toolClass : toolClasses) {
            if (toolClass.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
