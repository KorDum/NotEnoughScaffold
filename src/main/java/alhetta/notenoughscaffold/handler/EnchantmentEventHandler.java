package alhetta.notenoughscaffold.handler;

import alhetta.notenoughscaffold.registry.EnchantmentRegistry;
import alhetta.notenoughscaffold.util.IdentityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Map;
import java.util.Random;

public class EnchantmentEventHandler {
    public static EnchantmentEventHandler INSTANCE = new EnchantmentEventHandler();

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBlockBreakEvent(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player.isSneaking()) {
            return;
        }

        ItemStack toolStack = player.getHeldItemMainhand();
        boolean isShovel = IdentityUtil.isShovel(toolStack);
        boolean isPickaxe = IdentityUtil.isPickaxe(toolStack);
        if (!isShovel && !isPickaxe) {
            return;
        }

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(toolStack);
        if (!enchantments.containsKey(EnchantmentRegistry.BIG_HOLES)) {
            return;
        }

        Enchantment unbreaking = Enchantment.REGISTRY.getObject(Enchantments.UNBREAKING.getRegistryName());
        int chance = 100;
        if (enchantments.containsKey(unbreaking)) {
            chance /= enchantments.get(unbreaking) + 1;
        }

        World world = event.getWorld();
        if (!canHarvest(world, event.getPos(), isShovel, isPickaxe)) {
            return;
        }

        BlockPos pos = event.getPos();
        EnumFacing side = EnumFacing.getDirectionFromEntityLiving(pos, player);
        Random random = new Random();
        int damage = 0;

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }

                if (side == EnumFacing.DOWN || side == EnumFacing.UP) {
                    pos = event.getPos().add(i, 0, j);
                } else if (side == EnumFacing.EAST || side == EnumFacing.WEST) {
                    pos = event.getPos().add(0, i, j);
                } else {
                    pos = event.getPos().add(i, j, 0);
                }

                if (!canHarvest(world, pos, isShovel, isPickaxe)) {
                    continue;
                }

                IBlockState blockState = world.getBlockState(pos);
                Block block = blockState.getBlock();
                block.harvestBlock(world, player, pos, blockState, null, toolStack);
                world.setBlockToAir(pos);

                if (!player.isCreative() && random.nextInt(100) <= chance) {
                    damage++;
                }
            }
        }

        toolStack.setItemDamage(toolStack.getItemDamage() + damage);
    }

    private boolean canHarvest(World world, BlockPos pos, boolean isShovel, boolean isPickaxe) {
        return (isShovel && canHarvestByShovel(world, pos))
            || (isPickaxe && canHarvestByPickaxe(world, pos));
    }

    private boolean canHarvestByPickaxe(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        String requiredTool = block.getHarvestTool(blockState);
        return block != Blocks.OBSIDIAN
            && !block.hasTileEntity(blockState)
            && requiredTool != null
            && requiredTool.equals("pickaxe");
    }

    private boolean canHarvestByShovel(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        String requiredTool = blockState.getBlock().getHarvestTool(blockState);
        return requiredTool != null && requiredTool.equals("shovel");
    }
}
