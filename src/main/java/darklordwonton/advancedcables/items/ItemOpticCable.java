package darklordwonton.advancedcables.items;

import java.util.List;

import darklordwonton.advancedcables.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemOpticCable extends ItemBlock {

	public ItemOpticCable(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add("Max Transfer: " + ConfigHandler.opticMax + " rf/t");
		if (ConfigHandler.opticLoss == 0)
			tooltip.add("No Loss");
		else
			tooltip.add("Loss: " + ConfigHandler.opticLoss + " rf/b");
	}
}
