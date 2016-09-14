package gggamer.advancedcables.blocks;

import gggamer.advancedcables.tileentities.CableTileEntity;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCoveredCopperCable extends BaseCableBlock implements ITileEntityProvider {

	public BlockCoveredCopperCable(Material materialIn) {
		super(materialIn);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		// TODO Auto-generated method stub
		return new CableTileEntity();
	}
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		CableTileEntity tileEntity = (CableTileEntity) world.getTileEntity(pos);
		tileEntity.init(128, 1, true);
		super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

}
