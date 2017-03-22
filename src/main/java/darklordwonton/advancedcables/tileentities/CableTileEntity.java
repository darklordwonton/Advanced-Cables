package darklordwonton.advancedcables.tileentities;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyConnection;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.TileEnergyHandler;
import darklordwonton.advancedcables.AdvancedCablesMain;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHealth;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;

public class CableTileEntity extends TileEntity implements ITickable, IEnergyReceiver, IEnergyProvider, IEnergyStorage {
	
	protected EnergyStorage storage = new EnergyStorage(Integer.MAX_VALUE);
	public int maxCapacity;
	public int loss;
	public boolean covered;
	public List sidesReceivedFrom = new ArrayList();
	public List sides = new IntArrayList();
	public List rendersides = new IntArrayList();
	public List boxes = new ArrayList();
	public List<EntityLivingBase> toBeToBeShocked = new ArrayList(); 
	public List<EntityLivingBase> toBeShocked = new ArrayList(); 
	public int currentPower;
	
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
		if (from == null) {
			int energyReceived = storage.receiveEnergy(maxReceive, simulate);
			if (this.storage.getEnergyStored() > this.maxCapacity)
				melt();
			return energyReceived;
		}
		if ((Integer)sides.get(from.getIndex()) < 2) {
			int energyReceived = storage.receiveEnergy(maxReceive, simulate);
			if (this.storage.getEnergyStored() > this.maxCapacity) {
				if (getWorld().getTileEntity(getPos().add(from.getDirectionVec())) instanceof CableTileEntity)
					((CableTileEntity)getWorld().getTileEntity(getPos().add(from.getDirectionVec()))).melt();
				melt();
			}
			if (energyReceived > 0) {
				sidesReceivedFrom.add(from);
			}
			return energyReceived;
		} else {
			return 0;
		}
	}
	
	public void melt() {
		IBlockState state = this.worldObj.getBlockState(this.getPos());
		state = Blocks.FLOWING_LAVA.getStateFromMeta(Math.max(1, (int) (8 - Math.sqrt(maxCapacity) / 16)));
		this.worldObj.setBlockState(this.getPos(), state);
		this.worldObj.scheduleUpdate(pos, this.worldObj.getBlockState(pos).getBlock(), 0);
		this.worldObj.removeTileEntity(this.getPos());
	}
	
	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		if (from == null)
			return Math.max(0, storage.extractEnergy(maxExtract, simulate) - loss);
		if ((Integer)sides.get(from.getIndex()) == 0 || (Integer)sides.get(from.getIndex()) == 2) {
			return Math.max(0, storage.extractEnergy(maxExtract, simulate) - loss);
		} else {
			return 0;
		}
	}

	@Override
	public void update() {
		List sidesPoweredTo = new ArrayList();
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
					if (tileEntity instanceof IEnergyProvider && ((IEnergyProvider)tileEntity).canConnectEnergy(side.getOpposite()) && this.canConnectEnergy(side)){
						this.receiveEnergy(side, ((IEnergyProvider)tileEntity).extractEnergy(side.getOpposite(), ((IEnergyProvider)tileEntity).getMaxEnergyStored(side.getOpposite()), false), false);
					}
					if (tileEntity instanceof IEnergyStorage && ((IEnergyStorage)tileEntity).canExtract()) {
						this.receiveEnergy(((IEnergyStorage)tileEntity).extractEnergy(((IEnergyStorage)tileEntity).getMaxEnergyStored(), false), false);
					}
				}
			}
		}
		this.currentPower = this.getEnergyStored(null);
		if (!covered) {
			if (this.storage.getEnergyStored() > 0) {
				this.shockEntities();
			}
		}
		for (int i = 0; i < 6; i++) {
			EnumFacing side = EnumFacing.getFront(i);
			Vec3i offset = new Vec3i(side.getFrontOffsetX(), side.getFrontOffsetY(), side.getFrontOffsetZ());
			if (!(sidesReceivedFrom.contains(side))) {
				if (this.worldObj.getTileEntity(this.getPos().add(offset)) != null) {
					TileEntity tileEntity = this.worldObj.getTileEntity(this.getPos().add(offset));
					if ((tileEntity instanceof IEnergyReceiver && ((IEnergyReceiver)tileEntity).canConnectEnergy(side.getOpposite())) || (tileEntity instanceof IEnergyStorage && ((IEnergyStorage)tileEntity).canReceive())){
						powerSplit++;
						this.rendersides.add(this.sides.get(i));
					} else {
						if ((tileEntity instanceof IEnergyConnection && ((IEnergyConnection) tileEntity).canConnectEnergy(side.getOpposite())) || tileEntity instanceof IEnergyStorage) {
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
								if (this.extractEnergy(side, ((IEnergyReceiver) tileEntity).receiveEnergy(side.getOpposite(), Math.max(0,energytotransmit - loss), false), false) >= energytotransmit - loss * 2) {
									sidesPoweredTo.add(side);
								}
							}
						} else if (tileEntity instanceof IEnergyStorage && ((IEnergyStorage)tileEntity).canReceive()) {
							if ((Integer)sides.get(side.getIndex()) == 0 || (Integer)sides.get(side.getIndex()) == 2) {
								if (this.extractEnergy(((IEnergyStorage) tileEntity).receiveEnergy(Math.max(0,energytotransmit - loss), false), false) >= energytotransmit - loss * 2) {
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
								if (this.extractEnergy(side, ((IEnergyReceiver) tileEntity).receiveEnergy(side.getOpposite(), Math.max(0,energytotransmit - loss), false), false) >= energytotransmit - loss * 2) {
									sidesPoweredTo.add(side);
								}
							}
						} else if (tileEntity instanceof IEnergyStorage && ((IEnergyStorage)tileEntity).canReceive()) {
							if ((Integer)sides.get(side.getIndex()) == 0 || (Integer)sides.get(side.getIndex()) == 2) {
								if (this.extractEnergy(((IEnergyStorage) tileEntity).receiveEnergy(Math.max(0,energytotransmit - loss), false), false) >= energytotransmit - loss * 2) {
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
								if (this.extractEnergy(side, ((IEnergyReceiver) tileEntity).receiveEnergy(side.getOpposite(), Math.max(0,energytotransmit - loss), false), false) >= energytotransmit - loss * 2) {
									sidesPoweredTo.add(side);
								}
							}
						} else if (tileEntity instanceof IEnergyStorage && ((IEnergyStorage)tileEntity).canReceive()) {
							if ((Integer)sides.get(side.getIndex()) == 0 || (Integer)sides.get(side.getIndex()) == 2) {
								if (this.extractEnergy(((IEnergyStorage) tileEntity).receiveEnergy(Math.max(0,energytotransmit - loss), false), false) >= energytotransmit - loss * 2) {
									sidesPoweredTo.add(side);
								}
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
	
	public int getCurrentPower() {
		return this.currentPower;
	}
	
	public void shockEntities() {
		float rangeMultiplier = Math.min(0.25f + (float) Math.sqrt(this.storage.getEnergyStored())/512, 0.75f);
		BlockPos rangeOffset = new BlockPos(rangeMultiplier,rangeMultiplier,rangeMultiplier);
		BlockPos maxOffset = new BlockPos(1,1,1);
		AxisAlignedBB range = new AxisAlignedBB(this.pos.subtract(rangeOffset),this.pos.add(maxOffset).add(rangeOffset));
		List entities = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, range);
		for (int i = 0; i < entities.size(); i++) {
			shock((EntityLivingBase) entities.get(i), (float) Math.sqrt(this.storage.getEnergyStored())/4);
		}
	}
	
	public void shock(EntityLivingBase target, float damage) {
		target.attackEntityFrom(AdvancedCablesMain.electrocution, damage);
		target.setFire(10);
		this.getWorld().playSound(null, target.getPosition(), SoundEvents.BLOCK_NOTE_SNARE, SoundCategory.BLOCKS, 1.0f, 1.0f);
		target.addPotionEffect(new PotionEffect(Potion.getPotionById(2), 2, 6, true, false));
		target.addPotionEffect(new PotionEffect(Potion.getPotionById(4), 2, 1, true, false));
		target.addPotionEffect(new PotionEffect(Potion.getPotionById(24), 2, 1, true, false));
		target.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 2, -10, true, false));
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
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return storage.getMaxEnergyStored();
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return receiveEnergy(null, maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return extractEnergy(null, maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return getEnergyStored(null);
	}

	@Override
	public int getMaxEnergyStored() {
		return getMaxEnergyStored(null);
	}

	@Override
	public boolean canExtract() {
		return true;
	}

	@Override
	public boolean canReceive() {
		return true;
	}
}
