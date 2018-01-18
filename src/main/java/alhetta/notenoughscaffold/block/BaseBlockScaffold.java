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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BaseBlockScaffold extends Block {
    protected static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0.03125D, 0.0D, 0.03125D, 0.96875D, 1.0D, 0.96875D);

    public BaseBlockScaffold(Material material, float hardness, float resistance) {
        super(material);
        setHardness(hardness);
        setResistance(resistance);
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

        BlockPos playerPos = new BlockPos(
            Math.floor(playerIn.posX),
            playerIn.posY - 0.5,
            Math.floor(playerIn.posZ)
        );
        if (playerPos.equals(pos)) {
            return false;
        }

        BlockPos newPos = new BlockPos(pos);
        while (true) {
            newPos = newPos.up();
            IBlockState upBlock = worldIn.getBlockState(newPos);
            if (upBlock.getBlock() == state.getBlock()) {
                continue;
            }

            if (canPlaceBlockAt(worldIn, newPos)) {
                worldIn.setBlockState(newPos, state);
                break;
            }
            return false;
        }

        if (!playerIn.isCreative()) {
            stack.shrink(1);
        }

        worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), blockSoundType.getPlaceSound(), SoundCategory.BLOCKS, 1, 1, true);
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

    private boolean canBlockStay(World world, BlockPos pos) {
        if (checkDownBlock(world, pos.down())) {
            return true;
        }

        int sideBlockAvailable = getSideBlockAvailable();
        for (int i = 1; i <= sideBlockAvailable; i++) {
            BlockPos nearPos = pos.add(i, 0, 0);
            if (!checkSideBlock(world, nearPos)) {
                break;
            }
            if (checkDownBlock(world, nearPos.down())) {
                return true;
            }
        }

        for (int i = -1; i >= -sideBlockAvailable; i--) {
            BlockPos nearPos = pos.add(i, 0, 0);
            if (!checkSideBlock(world, nearPos)) {
                break;
            }
            if (checkDownBlock(world, nearPos.down())) {
                return true;
            }
        }

        for (int i = 1; i <= sideBlockAvailable; i++) {
            BlockPos nearPos = pos.add(0, 0, i);
            if (!checkSideBlock(world, nearPos)) {
                break;
            }
            if (checkDownBlock(world, nearPos.down())) {
                return true;
            }
        }

        for (int i = -1; i >= -sideBlockAvailable; i--) {
            BlockPos nearPos = pos.add(0, 0, i);
            if (!checkSideBlock(world, nearPos)) {
                break;
            }
            if (checkDownBlock(world, nearPos.down())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSideBlock(World world, BlockPos pos) {
        IBlockState sideBlock = world.getBlockState(pos);
        return sideBlock.getBlock() == this;
    }

    private boolean checkDownBlock(World world, BlockPos pos) {
        IBlockState bottomBlock = world.getBlockState(pos);
        Material material = bottomBlock.getMaterial();
        return material.isSolid();
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos)
            && canBlockStay(worldIn, pos);
    }

    private double limit(double value, double min, double max) {
        if (Double.isNaN(value) || value <= min) {
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

    protected abstract int getSideBlockAvailable();

    private enum State {
        SCAFFOLD
    }
}
