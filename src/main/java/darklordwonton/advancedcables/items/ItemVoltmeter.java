package darklordwonton.advancedcables.items;

import java.util.Set;

import darklordwonton.advancedcables.tileentities.CableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemVoltmeter extends Item {
	
	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			TileEntity tileEntity = world.getTileEntity(pos);
			if (tileEntity instanceof CableTileEntity) {
				CableTileEntity cableEntity = (CableTileEntity) tileEntity;
				player.addChatComponentMessage(new TextComponentString("Current RF Flow: " + cableEntity.getCurrentPower() + " rf/t"));
			}
		}
        return EnumActionResult.SUCCESS;
    }
	
}
