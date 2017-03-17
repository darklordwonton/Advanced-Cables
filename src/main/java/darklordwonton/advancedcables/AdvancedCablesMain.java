package darklordwonton.advancedcables;

import java.util.List;

import darklordwonton.advancedcables.blocks.*;
import darklordwonton.advancedcables.items.*;
import darklordwonton.advancedcables.tileentities.*;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = AdvancedCablesMain.MODID, version = AdvancedCablesMain.VERSION, name = AdvancedCablesMain.NAME)
public class AdvancedCablesMain {
	@SidedProxy(clientSide = "darklordwonton.advancedcables.ClientProxy", serverSide = "darklordwonton.advancedcables.ServerProxy")
	public static CommonProxy proxy;
	
	static final String MODID = "advancedcables";
	static final String VERSION = "2.0.0";
	static final String NAME = "advancedcables";
	
	public static final DamageSource electrocution = new DamageSource("electric")
			.setDamageBypassesArmor();
	
	static CreativeTabs tabadvancedcables = new TabAdvancedCables(CreativeTabs.getNextID(), "tabadvancedcables");
	
	static Block copper_cable = new BlockCopperCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("copper_cable")
			.setRegistryName("copper_cable");
	
	static Item icopper_cable = new ItemCopperCable(copper_cable)
			.setRegistryName("copper_cable")
			.setUnlocalizedName("copper_cable");
	
	static Block covered_copper_cable = new BlockCoveredCopperCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_copper_cable")
			.setRegistryName("covered_copper_cable");
	
	static Item icovered_copper_cable = new ItemCoveredCopperCable(covered_copper_cable)
			.setRegistryName("covered_copper_cable")
			.setUnlocalizedName("covered_copper_cable");
	
	static Block tin_cable = new BlockTinCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("tin_cable")
			.setRegistryName("tin_cable");
	
	static Item itin_cable = new ItemTinCable(tin_cable)
			.setRegistryName("tin_cable")
			.setUnlocalizedName("tin_cable");
	
	static Block covered_tin_cable = new BlockCoveredTinCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_tin_cable")
			.setRegistryName("covered_tin_cable");
	
	static Item icovered_tin_cable = new ItemCoveredTinCable(covered_tin_cable)
			.setRegistryName("covered_tin_cable")
			.setUnlocalizedName("covered_tin_cable");
	
	static Block silver_cable = new BlockSilverCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("silver_cable")
			.setRegistryName("silver_cable");
	
	static Item isilver_cable = new ItemSilverCable(silver_cable)
			.setRegistryName("silver_cable")
			.setUnlocalizedName("silver_cable");
	
	static Block covered_silver_cable = new BlockCoveredSilverCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_silver_cable")
			.setRegistryName("covered_silver_cable");
	
	static Item icovered_silver_cable = new ItemCoveredSilverCable(covered_silver_cable)
			.setRegistryName("covered_silver_cable")
			.setUnlocalizedName("covered_silver_cable");
	
	static Block gold_cable = new BlockGoldCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("gold_cable")
			.setRegistryName("gold_cable");
	
	static Item igold_cable = new ItemGoldCable(gold_cable)
			.setRegistryName("gold_cable")
			.setUnlocalizedName("gold_cable");
	
	static Block covered_gold_cable = new BlockCoveredGoldCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_gold_cable")
			.setRegistryName("covered_gold_cable");
	
	static Item icovered_gold_cable = new ItemCoveredGoldCable(covered_gold_cable)
			.setRegistryName("covered_gold_cable")
			.setUnlocalizedName("covered_gold_cable");
	
	static Block pink_cable = new BlockPinkCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("pink_cable")
			.setRegistryName("pink_cable");
	
	static Item ipink_cable = new ItemPinkCable(pink_cable)
			.setRegistryName("pink_cable")
			.setUnlocalizedName("pink_cable");
	
	static Block covered_pink_cable = new BlockCoveredPinkCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_pink_cable")
			.setRegistryName("covered_pink_cable");
	
	static Item icovered_pink_cable = new ItemCoveredPinkCable(covered_pink_cable)
			.setRegistryName("covered_pink_cable")
			.setUnlocalizedName("covered_pink_cable");
	
	static Block ender_cable = new BlockEnderCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("ender_cable")
			.setRegistryName("ender_cable");
	
	static Item iender_cable = new ItemEnderCable(ender_cable)
			.setRegistryName("ender_cable")
			.setUnlocalizedName("ender_cable");
	
	static Block covered_ender_cable = new BlockCoveredEnderCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_ender_cable")
			.setRegistryName("covered_ender_cable");
	
	static Item icovered_ender_cable = new ItemCoveredEnderCable(covered_ender_cable)
			.setRegistryName("covered_ender_cable")
			.setUnlocalizedName("covered_ender_cable");
	
	static Block super_cable = new BlockSuperCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("super_cable")
			.setRegistryName("super_cable");
	
	static Item isuper_cable = new ItemSuperCable(super_cable)
			.setRegistryName("super_cable")
			.setUnlocalizedName("super_cable");
	
	static Block covered_super_cable = new BlockCoveredSuperCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("covered_super_cable")
			.setRegistryName("covered_super_cable");
	
	static Item icovered_super_cable = new ItemCoveredSuperCable(covered_super_cable)
			.setRegistryName("covered_super_cable")
			.setUnlocalizedName("covered_super_cable");
	
	static Block optic_cable = new BlockOpticCable(Material.CLOTH)
			.setCreativeTab(tabadvancedcables)
			.setUnlocalizedName("optic_cable")
			.setRegistryName("optic_cable");
	
	static Item ioptic_cable = new ItemOpticCable(optic_cable)
			.setRegistryName("optic_cable")
			.setUnlocalizedName("optic_cable");
	
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		proxy.preinit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}
}
