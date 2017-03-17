package darklordwonton.advancedcables;

import darklordwonton.advancedcables.tileentities.CableTileEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class CommonProxy {
	public void preinit(FMLPreInitializationEvent event) {
		GameRegistry.registerTileEntity(CableTileEntity.class, "copper_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_copper_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "tin_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_tin_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "silver_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_silver_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "gold_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_gold_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "pink_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_pink_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "ender_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_ender_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "optic_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "super_cable");
		GameRegistry.registerTileEntity(CableTileEntity.class, "covered_super_cable");
		
		TileEntity.addMapping(CableTileEntity.class, "cable");
		
		GameRegistry.register(AdvancedCablesMain.copper_cable);
		GameRegistry.register(AdvancedCablesMain.icopper_cable);
		GameRegistry.register(AdvancedCablesMain.covered_copper_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_copper_cable);
		GameRegistry.register(AdvancedCablesMain.tin_cable);
		GameRegistry.register(AdvancedCablesMain.itin_cable);
		GameRegistry.register(AdvancedCablesMain.covered_tin_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_tin_cable);
		GameRegistry.register(AdvancedCablesMain.silver_cable);
		GameRegistry.register(AdvancedCablesMain.isilver_cable);
		GameRegistry.register(AdvancedCablesMain.covered_silver_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_silver_cable);
		GameRegistry.register(AdvancedCablesMain.gold_cable);
		GameRegistry.register(AdvancedCablesMain.igold_cable);
		GameRegistry.register(AdvancedCablesMain.covered_gold_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_gold_cable);
		GameRegistry.register(AdvancedCablesMain.pink_cable);
		GameRegistry.register(AdvancedCablesMain.ipink_cable);
		GameRegistry.register(AdvancedCablesMain.covered_pink_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_pink_cable);
		GameRegistry.register(AdvancedCablesMain.ender_cable);
		GameRegistry.register(AdvancedCablesMain.iender_cable);
		GameRegistry.register(AdvancedCablesMain.covered_ender_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_ender_cable);
		GameRegistry.register(AdvancedCablesMain.optic_cable);
		GameRegistry.register(AdvancedCablesMain.ioptic_cable);
		GameRegistry.register(AdvancedCablesMain.super_cable);
		GameRegistry.register(AdvancedCablesMain.isuper_cable);
		GameRegistry.register(AdvancedCablesMain.covered_super_cable);
		GameRegistry.register(AdvancedCablesMain.icovered_super_cable);
	}
	public void init(FMLInitializationEvent event) {
		ShapedOreRecipe copperCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.icopper_cable, 6), "AAA", 'A', "ingotCopper");
		GameRegistry.addRecipe(copperCable);
		ShapedOreRecipe tinCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.itin_cable, 6), "AAA", 'A', "ingotTin");
		GameRegistry.addRecipe(tinCable);
		ShapedOreRecipe silverCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.isilver_cable, 6), "AAA", 'A', "ingotSilver");
		GameRegistry.addRecipe(silverCable);
		ShapedOreRecipe goldCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.igold_cable, 6), "AAA", 'A', "ingotGold");
		GameRegistry.addRecipe(goldCable);
		ShapedOreRecipe pinkCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.ipink_cable, 6), "AAA", "BBB", "AAA", 'A', "dyePink",'B', "ingotNickel");
		GameRegistry.addRecipe(pinkCable);
		ShapedOreRecipe enderCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.iender_cable, 3), "AAA", 'A', "ingotEnderium");
		GameRegistry.addRecipe(enderCable);
		ShapedOreRecipe opticCable = new ShapedOreRecipe(new ItemStack(AdvancedCablesMain.ioptic_cable, 6), "AAA", "BBB", "AAA", 'A', "blockGlass",'B', "gemDiamond");
		GameRegistry.addRecipe(opticCable);
		ShapedOreRecipe superCable = new ShapedOreRecipe(AdvancedCablesMain.isuper_cable, "AAA", 'A', "ingotSteel");
		GameRegistry.addRecipe(superCable);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_copper_cable), AdvancedCablesMain.icopper_cable, Items.CLAY_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_tin_cable), AdvancedCablesMain.itin_cable, Items.CLAY_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_silver_cable), AdvancedCablesMain.isilver_cable, Items.CLAY_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_gold_cable), AdvancedCablesMain.igold_cable, Items.CLAY_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_pink_cable), AdvancedCablesMain.ipink_cable, Items.CLAY_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_ender_cable), AdvancedCablesMain.iender_cable, Items.CLAY_BALL);
		GameRegistry.addShapelessRecipe(new ItemStack(AdvancedCablesMain.icovered_super_cable), AdvancedCablesMain.isuper_cable, Items.CLAY_BALL);
	}
	public void postinit(FMLPostInitializationEvent event) {
		
	}
}
