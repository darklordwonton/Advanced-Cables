package darklordwonton.advancedcables.items;

import java.util.List;

import darklordwonton.advancedcables.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCoveredEnderCable extends ItemBlock {

	public ItemCoveredEnderCable(Block block) {
		super(block);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
		tooltip.add("Max Transfer: " + ConfigHandler.enderMax + " rf/t");
		if (ConfigHandler.enderLoss == 0)
			tooltip.add("No Loss");
		else
			tooltip.add("Loss: " + (int) (ConfigHandler.enderLoss * ConfigHandler.coveredModifier + 0.5) + " rf/b");
	}
}
