package gggamer.advancedcables;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class TabAdvancedCables extends CreativeTabs {

	public TabAdvancedCables(int index, String label) {
		super(index, label);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Item getTabIconItem() {
		// TODO Auto-generated method stub
		return AdvancedCablesMain.icovered_copper_cable;
	}

}
