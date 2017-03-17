package darklordwonton.advancedcables.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCoveredGoldCable extends ItemBlock {

	public ItemCoveredGoldCable(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add("Max Transfer: 2048 rf/t");
		tooltip.add("Loss: 6 rf/b");
	}
}