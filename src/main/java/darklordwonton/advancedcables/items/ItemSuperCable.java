package darklordwonton.advancedcables.items;

import java.util.List;

import darklordwonton.advancedcables.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemSuperCable extends ItemBlock {

	public ItemSuperCable(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add("Max Transfer: " + ConfigHandler.superMax + " rf/t");
		if (ConfigHandler.superLoss == 0)
			tooltip.add("No Loss");
		else
			tooltip.add("Loss: " + ConfigHandler.superLoss + " rf/b");
	}
}
