package gggamer.advancedcables;

import gggamer.advancedcables.tileentities.CableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		
	}
	public void postinit(FMLPostInitializationEvent event) {
		
	}
}
