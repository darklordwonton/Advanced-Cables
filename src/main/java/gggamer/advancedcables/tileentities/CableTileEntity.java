package gggamer.advancedcables.tileentities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.TileEnergyHandler;
import gggamer.advancedcables.AdvancedCablesMain;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class CableTileEntity extends TileEntity implements ITickable, IEnergyHandler {
	
	protected EnergyStorage storage = new EnergyStorage(Integer.MAX_VALUE);
	public int maxCapacity;
	public int loss;
	public boolean covered;
	public List sidesReceivedFrom = new ArrayList();
	public List sides = new IntArrayList();
	public List rendersides = new IntArrayList();
	public List boxes = new ArrayList();
	
	public static AxisAlignedBB[] coveredBoxes = {new AxisAlignedBB(0.25,0,0.25,0.75,0.25,0.75),
			new AxisAlignedBB(0.25,0.75,0.25,0.75,1,0.75),
			new AxisAlignedBB(0.25,0.25,0,0.75,0.75,0.25),
			new AxisAlignedBB(0.25,0.25,0.75,0.75,0.75,1),
			new AxisAlignedBB(0,0.25,0.25,0.25,0.75,0.75),
			new AxisAlignedBB(0.75,0.25,0.25,1,0.75,0.75)
			};
	public static AxisAlignedBB[] uncoveredBoxes = {new AxisAlignedBB(0.3125,0,0.3125,0.6875,0.3125,0.6875),
			new AxisAlignedBB(0.3125,0.6875,0.3125,0.6875,1,0.6875),
			new AxisAlignedBB(0.3125,0.3125,0,0.6875,0.6875,0.3125),
			new AxisAlignedBB(0.3125,0.3125,0.6875,0.6875,0.6875,1),
			new AxisAlignedBB(0,0.3125,0.3125,0.3125,0.6875,0.6875),
			new AxisAlignedBB(0.6875,0.3125,0.3125,1,0.6875,0.6875)
			};
	public static String[] sideStates = {"no restrictions", "input only", "output only", "disabled"};
	
	public void init (int cap, int loss, boolean covered) {
		this.maxCapacity = cap;
		this.loss = loss;
		this.covered = covered;
		if (sides.size() != 6) {
			for (int i = 0; i < 6; i++) {
				sides.add(0);
			}
		}
	}
	
	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		if ((Integer)sides.get(from.getIndex()) < 2) {
			int energyReceived = storage.receiveEnergy(maxReceive, simulate);
			if (this.storage.getEnergyStored() > this.maxCapacity) {
				IBlockState state = this.worldObj.getBlockState(this.getPos());
				state = Blocks.FLOWING_LAVA.getStateFromMeta(Math.max(1, (int) (8 - Math.sqrt(maxCapacity) / 16)));
				this.worldObj.setBlockState(this.getPos(), state);
				this.worldObj.removeTileEntity(this.getPos());
			}
			sidesReceivedFrom.add(from);
			return energyReceived;
		} else {
			return 0;
		}
	}
	
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if ((Integer)sides.get(from.getIndex()) == 0 || (Integer)sides.get(from.getIndex()) == 2) {
			return storage.extractEnergy(maxExtract, simulate) - loss;
		} else {
			return 0;
		}
	}

	@Override
	public void update() {
		List sidesPoweredTo = new ArrayList();
		if ((!covered) && this.storage.getEnergyStored() > 0) {
			this.shockEntities();
		}
		if (sides.size() != 6) {
			for (int i = 0; i < 6; i++) {
				sides.add(0);
			}
		}
		float powerSplit = 0;
		rendersides.clear();
		for (int i = 0; i < 6; i++) {
			EnumFacing side = EnumFacing.getFront(i);
			Vec3i offset = new Vec3i(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
			if (!(sidesReceivedFrom.contains(side))) {
				if (this.worldObj.getTileEntity(this.getPos().add(offset)) != null) {
					TileEntity tileEntity = this.worldObj.getTileEntity(this.getPos().add(offset));
					if (tileEntity instanceof IEnergyReceiver && ((IEnergyReceiver)tileEntity).canConnectEnergy(side.getOpposite())){
						powerSplit++;
						this.rendersides.add(this.sides.get(i));
					} else {
						if (tileEntity instanceof IEnergyConnection && ((IEnergyConnection) tileEntity).canConnectEnergy(side.getOpposite())) {
							this.rendersides.add(this.sides.get(i));
						} else {
							this.rendersides.add(3);
						}
					}
				} else {
					this.rendersides.add(3);
				}
			} else {
				this.rendersides.add(this.sides.get(i));
			}
		}
		if (this.storage.getEnergyStored() > 0){
			if (powerSplit > 0) {
				int energytotransmit = (int) Math.floor(this.storage.getEnergyStored()/powerSplit);
				for (int i = 0; i < 6; i++) {
					EnumFacing side = EnumFacing.getFront(i);
					Vec3i offset = new Vec3i(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
					if (!(sidesReceivedFrom.contains(side)) 
							&& (this.worldObj.getTileEntity(this.getPos().add(offset)) != null)) {
						TileEntity tileEntity = this.worldObj.getTileEntity(this.getPos().add(offset));
						if (tileEntity instanceof IEnergyReceiver && ((IEnergyReceiver)tileEntity).canConnectEnergy(side.getOpposite())){
							if ((Integer)sides.get(side.getIndex()) == 0 || (Integer)sides.get(side.getIndex()) == 2) {
								if (this.extractEnergy(side, ((IEnergyReceiver) tileEntity).receiveEnergy(side.getOpposite(), energytotransmit - loss, false), false) >= energytotransmit - loss - 1) {
									sidesPoweredTo.add(side);
								}
							}
						}
					}
				}
			} else if (sidesReceivedFrom.size() > 0){
				powerSplit = sidesReceivedFrom.size();
				int energytotransmit = (int) Math.floor(this.storage.getEnergyStored()/powerSplit);
				for (int i = 0; i < 6; i++) {
					EnumFacing side = EnumFacing.getFront(i);
					Vec3i offset = new Vec3i(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
					if (sidesReceivedFrom.contains(side) 
							&& (this.worldObj.getTileEntity(this.getPos().add(offset)) != null)) {
						TileEntity tileEntity = this.worldObj.getTileEntity(this.getPos().add(offset));
						if (tileEntity instanceof IEnergyReceiver && ((IEnergyReceiver)tileEntity).canConnectEnergy(side.getOpposite())){
							if ((Integer)sides.get(side.getIndex()) == 0 || (Integer)sides.get(side.getIndex()) == 2) {
								if (this.extractEnergy(side, ((IEnergyReceiver) tileEntity).receiveEnergy(side.getOpposite(), energytotransmit - loss, false), false) >= energytotransmit - loss - 1) {
									sidesPoweredTo.add(side);
								}	
							}
						}
					}
				}
			}
			if (this.storage.getEnergyStored() > loss && sidesPoweredTo.size() > 0) {
				powerSplit = sidesPoweredTo.size();
				int energytotransmit = (int) Math.floor(this.storage.getEnergyStored()/powerSplit);
				for (int i = 0; i < 6; i++) {
					EnumFacing side = EnumFacing.getFront(i);
					Vec3i offset = new Vec3i(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
					if (sidesPoweredTo.contains(side) 
							&& (this.worldObj.getTileEntity(this.getPos().add(offset)) != null)) {
						TileEntity tileEntity = this.worldObj.getTileEntity(this.getPos().add(offset));
						if (tileEntity instanceof IEnergyReceiver && ((IEnergyReceiver)tileEntity).canConnectEnergy(side.getOpposite())){
							if ((Integer)sides.get(side.getIndex()) == 0 || (Integer)sides.get(side.getIndex()) == 2) {
								this.extractEnergy(side, ((IEnergyReceiver) tileEntity).receiveEnergy(side.getOpposite(), this.extractEnergy(side, energytotransmit, true), false), false);
							}
						}
					}
				}
			}
		}
		
		boxes.clear();
		if (this.covered) {
			boxes.add(new AxisAlignedBB(0.25,0.25,0.25,0.75,0.75,0.75));
		} else {
			boxes.add(new AxisAlignedBB(0.3125,0.3125,0.3125,0.6875,0.6875,0.6875));
		}
		for (int i = 0; i < 6; i++) {
			if ((Integer)rendersides.get(i) < 3) {
				if (this.covered) {
					boxes.add(coveredBoxes[i]);
				} else {
					boxes.add(uncoveredBoxes[i]);
				}
			}
		}
		
		sidesReceivedFrom.clear();
		this.storage.setEnergyStored(0);
	}
	
	public void shockEntities() {
		float rangeMultiplier = 0.25f + (float) Math.sqrt(this.storage.getEnergyStored())/1024;
		BlockPos rangeOffset = new BlockPos(rangeMultiplier,rangeMultiplier,rangeMultiplier);
		BlockPos maxOffset = new BlockPos(1,1,1);
		AxisAlignedBB range = new AxisAlignedBB(this.pos.subtract(rangeOffset),this.pos.add(maxOffset).add(rangeOffset));
		List entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);
		for (int i = 0; i < entities.size(); i++) {
			((EntityLivingBase) entities.get(i)).attackEntityFrom(AdvancedCablesMain.electrocution, (float) Math.sqrt(this.storage.getEnergyStored())/8);
		}
	}
	
	public void incrementSide (int side, EntityPlayer player, World world) {
		sides.set(side, (Integer)sides.get(side) + 1);
		if ((Integer)sides.get(side) > 3) {
			sides.set(side, 0);
		}
		if (world.isRemote) {
			player.addChatComponentMessage(new TextComponentString(EnumFacing.getFront(side) + " side set to " + sideStates[(Integer)sides.get(side)]));
		}
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing side) {
		if (side.getIndex() < sides.size()) {
			return ((Integer)sides.get(side.getIndex()) < 3);
		} else {
			return false;
		}
	}
	
	public int getSideState (int side) {
		return (Integer) sides.get(side);
	}
	
	public int getSideState (EnumFacing side) {
		return (Integer) sides.get(side.getIndex());
	}
	
	
	@Override
	@Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound data = new NBTTagCompound();
    	if (sides.size() == 6) {
    		writeToNBT(data);
            return new SPacketUpdateTileEntity(this.pos, 1, data);
    	} else {
			for (int i = 0; i < 6; i++) {
				sides.add(0);
			}
    		return null;
    	}
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
        readFromNBT(pkt.getNbtCompound());
        worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
    }
    
    @Override
    public NBTTagCompound getUpdateTag()
    {
    	if (sides.size() == 6) {
    		return this.writeToNBT(new NBTTagCompound());
    	} else {
			for (int i = 0; i < 6; i++) {
				sides.add(0);
			}
    		return super.getUpdateTag();
    	}
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		storage.readFromNBT(nbt);
		sides.clear();
		this.maxCapacity = nbt.getInteger("maxCapacity");
		this.loss = nbt.getInteger("loss");
		this.covered = nbt.getBoolean("covered");
		for (int i = 0; i < 6; i++) {
			sides.add(nbt.getIntArray("sides")[i]);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		storage.writeToNBT(nbt);
		int[] sidesintarray = new int[6];
		for(int i = 0; i < 6; i++) {
			sidesintarray[i] = ((Integer)sides.get(i)).intValue();
		};
		nbt.setIntArray("sides", sidesintarray);
		nbt.setInteger("maxCapacity", this.maxCapacity);
		nbt.setInteger("loss", this.loss);
		nbt.setBoolean("covered", this.covered);
		return nbt;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		// TODO Auto-generated method stub
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		// TODO Auto-generated method stub
		return storage.getMaxEnergyStored();
	}
}
