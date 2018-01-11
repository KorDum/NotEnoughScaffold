package alhetta.notenoughscaffold.enchantment;

import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;

import alhetta.notenoughscaffold.util.IdentityUtil;

class EnchantmentEventHandler {
    public static EnchantmentEventHandler INSTANCE = new EnchantmentEventHandler();
    
    private EnumFacing side;

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        side = event.getFace();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        ItemStack stack = player.getHeldItemMainhand();
        if (!IdentityUtil.isShovel(stack)) {
            return;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        if (!enchantments.containsKey(EnchantmentRegistry.BIG_HOLES)) {
            return;
        }

        World world = event.getWorld();
        if (!canHarvest(world, event.getPos())) {
            return;
        }

        int damage = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                BlockPos pos;
                if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
                    pos = event.getPos().add(i, 0, j);
                } else if (side == EnumFacing.EAST || side == EnumFacing.WEST) {
                    pos = event.getPos().add(0, i, j);
                } else {
                    pos = event.getPos().add(i, j, 0);
                }

                if (!canHarvest(world, pos)) {
                    continue;
                }

                world.destroyBlock(pos, true);
                damage++;
            }
        }

        stack.setItemDamage(stack.getItemDamage() + damage);
    }

    private boolean canHarvest(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        String requiredTool = blockState.getBlock().getHarvestTool(blockState);
        return requiredTool != null && requiredTool.equals("shovel");
    }
}
