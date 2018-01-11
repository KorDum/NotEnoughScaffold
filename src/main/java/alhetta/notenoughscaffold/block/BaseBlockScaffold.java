package alhetta.notenoughscaffold.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BaseBlockScaffold extends BlockBase {
    protected static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.03125D, 0.0D, 0.03125D, 0.96875D, 1.0D, 0.96875D);

    public BaseBlockScaffold(Material material, String name, float hardness, float resistance) {
        super(material, name, hardness, resistance);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);
        if (stack.isEmpty()) {
            return false;
        }

        Block handBlock = Block.getBlockFromItem(stack.getItem());
        if (handBlock != state.getBlock()) {
            return false;
        }

        BlockPos newPos = new BlockPos(pos);
        while (true) {
            newPos = newPos.up();
            IBlockState upBlock = worldIn.getBlockState(newPos);
            if (upBlock.getBlock() == state.getBlock()) {
                continue;
            }

            if (worldIn.isAirBlock(newPos)) {
                worldIn.setBlockState(newPos, state);
                break;
            }
            return false;
        }

        if (!playerIn.isCreative()) {
            stack.shrink(1);
        }
        return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!(entityIn instanceof EntityLivingBase)) {
            return;
        }

        EntityLivingBase entity = (EntityLivingBase) entityIn;
        entity.fallDistance = 0.0F;
        double limit = 0.15D;
        entity.motionX = limit(entity.motionX, -limit, limit);
        entity.motionZ = limit(entity.motionZ, -limit, limit);

        if (entity.isSneaking() && entity instanceof EntityPlayer) {
            if (entity.isInWater()) {
                entity.motionY = 0.02D;
            } else {
                entity.motionY = 0.08D;
            }
        } else if (entity.collidedHorizontally) {
            entity.motionY = getMotionSpeed();
        } else {
            entity.motionY = Math.max(entity.motionY, -0.07D);
        }
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        super.onBlockAdded(worldIn, pos, state);
        if (!canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!canBlockStay(worldIn, pos)) {
            worldIn.destroyBlock(pos, true);
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        IBlockState bottomBlock = worldIn.getBlockState(pos.down());
        Material material = bottomBlock.getMaterial();
        return material != Material.AIR && material.isSolid();
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return canBlockStay(worldIn, pos);
    }

    private double limit(double value, double min, double max) {
        if ((Double.isNaN(value)) || (value <= min)) {
            return min;
        }
        if (value >= max) {
            return max;
        }
        return value;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return COLLISION_AABB;
    }

    protected abstract double getMotionSpeed();
}
