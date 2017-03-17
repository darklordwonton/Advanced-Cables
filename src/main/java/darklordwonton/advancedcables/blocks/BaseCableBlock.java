package darklordwonton.advancedcables.blocks;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import darklordwonton.advancedcables.tileentities.CableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BaseCableBlock extends Block{
	
	static final IProperty<Boolean> down = PropertyBool.create("down");
	static final IProperty<Boolean> up = PropertyBool.create("up");
	static final IProperty<Boolean> north = PropertyBool.create("north");
	static final IProperty<Boolean> south = PropertyBool.create("south");
	static final IProperty<Boolean> west = PropertyBool.create("west");
	static final IProperty<Boolean> east = PropertyBool.create("east");
	protected AxisAlignedBB boundbox;
	protected int lookingSide;

	public BaseCableBlock(Material materialIn) {
		super(Material.LEAVES);
		// TODO Auto-generated constructor stub
		this.setSoundType(SoundType.METAL);
		this.fullBlock = false;
		this.setLightOpacity(0);
		this.setHardness(0.2f);
		this.setHarvestLevel("shears", 0);
	}
	
	@Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
		CableTileEntity tileEntity = (CableTileEntity) world.getTileEntity(pos);
		if (tileEntity != null && tileEntity.getEnergyStored(null) > 0 && !tileEntity.covered) {
			tileEntity.shock(player, (float) Math.sqrt(tileEntity.getCurrentPower())); 
		}
    }
	
	@Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        super.breakBlock(world, pos, state);
        world.removeTileEntity(pos);
    }
	
	@Override
	public boolean doesSideBlockRendering (IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing facing) {
		return false;
	}
	
	@Override
	public boolean isNormalCube (IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		BlockStateContainer blockstate = new BlockStateContainer(this, up, down, east, west, north, south);
		return blockstate;
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox (IBlockState state, World world, BlockPos pos) {
		return this.boundbox.offset(pos);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}
	
	@Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn)
    {
		CableTileEntity tileEntity = (CableTileEntity) world.getTileEntity(pos);
		List<AxisAlignedBB> boxes = tileEntity.boxes;
		for (int i = 0; i < boxes.size(); i++ ) {
	        addCollisionBoxToList(pos, entityBox, collidingBoxes, boxes.get(i));
		}
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
		CableTileEntity tileEntity = (CableTileEntity) world.getTileEntity(pos);
		List<AxisAlignedBB> boxes = tileEntity.boxes;
		
		List<RayTraceResult> rayTraces = new ArrayList();
		for (int i = 0; i < boxes.size(); i++ ) {
	        rayTraces.add(this.rayTrace(pos, start, end, boxes.get(i)));
		}
		
		RayTraceResult farRay = null;
		double dist = 0;
		
		for (int i = 0; i < rayTraces.size(); i++ ) {
			if (rayTraces.get(i) != null) {
				if (rayTraces.get(i).hitVec.squareDistanceTo(end) > dist) {
					farRay = rayTraces.get(i);
					dist = rayTraces.get(i).hitVec.squareDistanceTo(end);
					this.boundbox = boxes.get(i);
				}
			}
		}
		
		this.lookingSide = -1;
		
		for (int i = 0; i < 6; i++) {
			if (tileEntity.covered) {
				if (this.boundbox == tileEntity.coveredBoxes[i]) {
					this.lookingSide = i;
				}
			} else {
				if (this.boundbox == tileEntity.uncoveredBoxes[i]) {
					this.lookingSide = i;
				}
			}
		}
		if (this.lookingSide == -1 && farRay != null) {
			this.lookingSide = farRay.sideHit.getIndex();
		}
				
		return farRay;
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
    {
    	if (player.inventory.getCurrentItem() != null) {
    		Item item = player.inventory.getCurrentItem().getItem();
    		CableTileEntity tileEntity = (CableTileEntity) world.getTileEntity(pos);
    		if (item instanceof ItemShears) {
        		if (!tileEntity.covered && tileEntity.getEnergyStored(null) > 0) {
        			tileEntity.shock(player, (float) Math.sqrt(tileEntity.getCurrentPower()));
        		}
    			tileEntity.incrementSide(this.lookingSide, player, world);
    			world.markBlockRangeForRenderUpdate(pos, pos.add(1,1,1));
    			return true;
    		}
    	}
        return false;
    }
	
	
	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
		CableTileEntity tileEntity = (CableTileEntity) world.getTileEntity(pos);
        return state.withProperty(down, ((Integer)tileEntity.rendersides.get(0)).intValue() < 3)
        .withProperty(up, ((Integer)tileEntity.rendersides.get(1)).intValue() < 3)
        .withProperty(north, ((Integer)tileEntity.rendersides.get(2)).intValue() < 3)
        .withProperty(south, ((Integer)tileEntity.rendersides.get(3)).intValue() < 3)
        .withProperty(west, ((Integer)tileEntity.rendersides.get(4)).intValue() < 3)
        .withProperty(east, ((Integer)tileEntity.rendersides.get(5)).intValue() < 3);
    }
	
	@Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {}
}
