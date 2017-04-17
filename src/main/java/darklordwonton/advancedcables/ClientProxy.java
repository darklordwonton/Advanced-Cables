package darklordwonton.advancedcables;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void preinit(FMLPreInitializationEvent event) {
        super.preinit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.copper_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":copper_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_copper_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_copper_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.tin_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":tin_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_tin_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_tin_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.silver_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":silver_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_silver_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_silver_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.gold_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":gold_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_gold_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_gold_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.pink_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":pink_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_pink_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_pink_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.ender_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":ender_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_ender_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_ender_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.optic_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":optic_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.super_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":super_cable", "inventory"));
		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(Item.getItemFromBlock(AdvancedCablesMain.covered_super_cable), 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":covered_super_cable", "inventory"));
        
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher()
	    .register(AdvancedCablesMain.voltmeter, 0, new ModelResourceLocation(AdvancedCablesMain.MODID+":voltmeter", "inventory"));
    }

    @Override
    public void postinit(FMLPostInitializationEvent event) {
        super.postinit(event);
    }
}
