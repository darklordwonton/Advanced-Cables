package darklordwonton.advancedcables;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {

	public static Configuration config;
	
	public static boolean cableMelting;
	public static float coveredModifier = 1.167f;
	
	public static int copperMax;
	public static int copperLoss;
	
	public static int tinMax;
	public static int tinLoss;
	
	public static int silverMax;
	public static int silverLoss;
	
	public static int goldMax;
	public static int goldLoss;
	
	public static int pinkMax ;
	public static int pinkLoss;
	
	public static int enderMax;
	public static int enderLoss;
	
	public static int opticMax ;
	public static int opticLoss;
	
	public static int superMax;
	public static int superLoss;
	
	public static void init(File file) {
		config = new Configuration(file);
		getConfigs();
	}
	
	private static void getConfigs() {
		
		String category = "general";
		cableMelting = config.getBoolean("cableMelting", category, true, "Set to false to disable cables melting when overloaded");
		coveredModifier = config.getFloat("coveredModifier", category, 1.167f, 0.0f, Float.MAX_VALUE, "Determines how many times more lossy covered cables are vs. uncovered cables (loss values are rounded to the nearest integer)");
		
		category = "copper";
		copperMax = config.getInt(category + "Max", category, 128, 1, Integer.MAX_VALUE, "Maximum tolerance for "+category+" cables in rf/t");
		copperLoss = config.getInt(category + "Loss", category, 1, 0, Integer.MAX_VALUE, "Loss for "+category+" cables in rf/b");
		
		category = "tin";
		tinMax = config.getInt(category + "Max", category, 32, 1, Integer.MAX_VALUE, "Maximum tolerance for "+category+" cables in rf/t");
		tinLoss = config.getInt(category + "Loss", category, 0, 0, Integer.MAX_VALUE, "Loss for "+category+" cables in rf/b");
		
		category = "silver";
		silverMax = config.getInt(category + "Max", category, 512, 1, Integer.MAX_VALUE, "Maximum tolerance for "+category+" cables in rf/t");
		silverLoss = config.getInt(category + "Loss", category, 3, 0, Integer.MAX_VALUE, "Loss for "+category+" cables in rf/b");
		
		category = "gold";
		goldMax = config.getInt(category + "Max", category, 2048, 1, Integer.MAX_VALUE, "Maximum tolerance for "+category+" cables in rf/t");
		goldLoss = config.getInt(category + "Loss", category, 5, 0, Integer.MAX_VALUE, "Loss for "+category+" cables in rf/b");
		
		category = "pink";
		pinkMax = config.getInt(category + "Max", category, 8192, 1, Integer.MAX_VALUE, "Maximum tolerance for "+category+" cables in rf/t");
		pinkLoss = config.getInt(category + "Loss", category, 15, 0, Integer.MAX_VALUE, "Loss for "+category+" cables in rf/b");
		
		category = "ender";
		enderMax = config.getInt(category + "Max", category, 16384, 1, Integer.MAX_VALUE, "Maximum tolerance for "+category+" cables in rf/t");
		enderLoss = config.getInt(category + "Loss", category, 18, 0, Integer.MAX_VALUE, "Loss for "+category+" cables in rf/b");
		
		category = "optic";
		opticMax = config.getInt(category + "Max", category, 65536, 1, Integer.MAX_VALUE, "Maximum tolerance for fibre optic cables in rf/t");
		opticLoss = config.getInt(category + "Loss", category, 70, 0, Integer.MAX_VALUE, "Loss for fibre optic cables in rf/b (note: despite not shocking entities fibre optic cables are not affected by the covered modifier)");
		
		category = "super";
		superMax = config.getInt(category + "Max", category, 100000000, 1, Integer.MAX_VALUE, "Maximum tolerance for high-voltage cables in rf/t");
		superLoss = config.getInt(category + "Loss", category, 300, 0, Integer.MAX_VALUE, "Loss for high-voltage cables in rf/b");
		
		config.save();
	}
}
