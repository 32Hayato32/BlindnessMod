package blindnessmod;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import blindnessmod.Creativetabs.BlindnessModTabs;
import blindnessmod.proxy.CommonProxy;
import blindnessmod.util.Handlers.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class BlindnessMod
{
	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
	public static CommonProxy proxy;

	public static final CreativeTabs BlindnessMod = new BlindnessModTabs();

	@Instance(Reference.MODID)
	public  static BlindnessMod INSTANCE;
	private static Logger logger;

	public  static List<String> WhiteBlockList = new ArrayList<String>();
	private static boolean LoadInit = true;

	@Mod.EventHandler
    public void construct(FMLConstructionEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event)
	{
		proxy.preInit(event);
		RegistryHandler.preInitRegistries(event);
	}

	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		proxy.init(event);
		RegistryHandler.initRegistries(event);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event)
	{
		proxy.postInit(event);
		RegistryHandler.postInitRegistries(event);
	}

	@EventHandler
	public static void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}

    @SubscribeEvent
	public void onLoadWorld(WorldEvent.Load e) {
		if(LoadInit) {
			ISaveHandler save = e.getWorld().getSaveHandler();
			if(save != null) {
				if(save.getWorldDirectory() != null) {
					System.out.println("------------LOAD START!---------");
					try {
					    List<String> lines = Files.readAllLines(Paths.get( save.getWorldDirectory().getAbsolutePath() + "/WhiteBlockList"), StandardCharsets.UTF_8);
						for(String str : lines)
							WhiteBlockList.add(str);
					} catch (IOException e1) {
					    e1.printStackTrace();
					}
					LoadInit = false;
					System.out.println("-------------LOAD END!----------");
				}
			}
		}
	}

	@SubscribeEvent
	public void onSaveEvent(WorldEvent.Save e) {
		ISaveHandler save = e.getWorld().getSaveHandler();
		if(save != null) {
			if(save.getWorldDirectory() != null) {
				System.out.println("------------SAVE START!---------");
				try {
					FileWriter file = new FileWriter(save.getWorldDirectory().getAbsolutePath() + "/WhiteBlockList");
					PrintWriter pw = new PrintWriter(new BufferedWriter(file));

					for(String s:WhiteBlockList)
						pw.println(s);

					pw.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println("-------------SAVE END!----------");
			}
		}
	}
}
