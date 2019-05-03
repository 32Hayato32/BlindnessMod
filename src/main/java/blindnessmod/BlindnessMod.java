package blindnessmod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import blindnessmod.Creativetabs.BlindnessModTabs;
import blindnessmod.Entitys.TESTEntity;
import blindnessmod.proxy.CommonProxy;
import blindnessmod.util.ItemInfo;
import blindnessmod.util.RegListUtil;
import blindnessmod.util.SpecialWhiteListUtil;
import blindnessmod.util.WhiteEntityListUtil;
import blindnessmod.util.WhiteListUtil;
import blindnessmod.util.XMLUtil;
import blindnessmod.util.Handlers.RegistryHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.world.World;
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
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class BlindnessMod
{

	public static int maxObjectSize = 1500000;
	public static double standingTolerance = .42D;

	@SidedProxy(clientSide = Reference.CLIENT, serverSide = Reference.SERVER)
	public static CommonProxy proxy;

	public static final CreativeTabs BlindnessMod = new BlindnessModTabs();

	@Instance(Reference.MODID)
	public  static BlindnessMod INSTANCE;
	private static Logger logger;

	public  static List<String> WhiteBlockList = new ArrayList<String>();
	public  static Map<String,String> RegBlockList = new HashMap<>();
	public static List<ItemInfo> RegItemList = new ArrayList<ItemInfo>();
	public static List<String>SpecialWhiteBlockList = new ArrayList<String>();
	public static List<String>WhiteEntityList = new ArrayList<String>();
	public static RegListUtil RegUtil = new RegListUtil();
	public static WhiteListUtil WhiteUtil = new WhiteListUtil();
	public static SpecialWhiteListUtil SpecialUtil = new SpecialWhiteListUtil();
	public static WhiteEntityListUtil EntityListUtil = new WhiteEntityListUtil();

	public static List<TESTEntity>entList = new ArrayList<TESTEntity>();

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
    	System.out.println("LoadWorld");
    	this.Load(e.getWorld());
    }

	@SubscribeEvent
	public void onSaveEvent(WorldEvent.Save e) {
		System.out.println("SaveWorld");
		this.Save(e.getWorld());
	}

	@SubscribeEvent
	public void onUnload(WorldEvent.Unload e) {
		System.out.println("UnloadWorld");
		this.Save(e.getWorld());
		this.LoadInit = true;
	}

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		System.out.println("Join!");
		this.Load(e.player.world);
	}

	private void Save(World w) {
		ISaveHandler save = w.getSaveHandler();
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
				try {
					FileWriter file = new FileWriter(save.getWorldDirectory().getAbsolutePath() + "/RegBlockList");
					PrintWriter pw = new PrintWriter(new BufferedWriter(file));

					for(String s:RegBlockList.keySet())
						pw.println(s + ":" + RegBlockList.get(s));

					pw.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					FileWriter file = new FileWriter(save.getWorldDirectory().getAbsolutePath() + "/SpecialWhiteBlockList");
					PrintWriter pw = new PrintWriter(new BufferedWriter(file));

					for(String s:SpecialWhiteBlockList)
						pw.println(s);
					pw.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					FileWriter file = new FileWriter(save.getWorldDirectory().getAbsolutePath() + "/WhiteEntityList");
					PrintWriter pw = new PrintWriter(new BufferedWriter(file));

					for(String s:WhiteEntityList)
						pw.println(s);
					pw.close();
				}catch (Exception e2) {
					e2.printStackTrace();
				}

				try {
					File file = new File(save.getWorldDirectory().getAbsolutePath() + "/RegItemList.xml");
					XMLUtil.CreateXML(file,RegItemList);

				}catch (Exception e2) {
					e2.printStackTrace();
				}

				System.out.println("-------------SAVE END!----------");
			}
		}
	}

	private void Load(World w) {
		if(LoadInit) {
			ISaveHandler save = w.getSaveHandler();
			if(save != null) {
				if(save.getWorldDirectory() != null) {
					System.out.println("------------LOAD START!---------");
					try {
					    List<String> lines = Files.readAllLines(Paths.get( save.getWorldDirectory().getAbsolutePath() + "/WhiteBlockList"), StandardCharsets.UTF_8);
						for(String str : lines)
							WhiteListUtil.Reg(str);
					} catch (IOException e1) {
					    e1.printStackTrace();
					}
					try {
					    List<String> lines = Files.readAllLines(Paths.get( save.getWorldDirectory().getAbsolutePath() + "/RegBlockList"), StandardCharsets.UTF_8);
						for(String str : lines) {
							String[] ss = str.split(":");
							RegBlockList.put(ss[0] + ":" + ss[1],ss[2] + ":" + ss[3]);
						}
					} catch (IOException e1) {
					    e1.printStackTrace();
					}
					try {
					    List<String> lines = Files.readAllLines(Paths.get( save.getWorldDirectory().getAbsolutePath() + "/SpecialWhiteBlockList"), StandardCharsets.UTF_8);
						for(String str : lines)
							SpecialUtil.Reg(str);
					} catch (IOException e1) {
					    e1.printStackTrace();
					}
					try {
					    List<String> lines = Files.readAllLines(Paths.get( save.getWorldDirectory().getAbsolutePath() + "/WhiteEntityList"), StandardCharsets.UTF_8);
						for(String str : lines)
							EntityListUtil.Reg(str);
					} catch (IOException e1) {
					    e1.printStackTrace();
					}

					try {
						DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
						DocumentBuilder builder;
						builder = factory.newDocumentBuilder();
						Document doc = builder.parse(save.getWorldDirectory().getAbsolutePath() + "/RegItemList.xml");
						Element root = doc.getDocumentElement();
						NodeList list = root.getChildNodes();
						for(int i = 0; i < list.getLength(); i++) {
							if(list.item(i).getNodeType() == Element.ELEMENT_NODE) {
								NodeList info = list.item(i).getChildNodes();
								ItemInfo item = new ItemInfo(ItemStack.EMPTY, 0, 0.0f);
								for(int j = 0; j < info.getLength(); j++) {
									if(info.item(j).getNodeType() == Element.ELEMENT_NODE) {
										switch (info.item(j).getNodeName()){
										case "ID":
											item.ID = Integer.parseInt(info.item(j).getTextContent());
											break;
										case "META":
											item.META = Integer.parseInt(info.item(j).getTextContent());
											break;
										case "COUNT":
											item.Count = Integer.parseInt(info.item(j).getTextContent());
											break;
										case "DisCOUNT":
											item.DisCount = Float.parseFloat(info.item(j).getTextContent());
											break;
										case "HASTAG":
											item.HasNBT = Boolean.parseBoolean(info.item(j).getTextContent());
											break;
										case "NBT":
											item.NBT = JsonToNBT.getTagFromJson(info.item(j).getTextContent());
											break;
										default:
											break;
										}
									System.out.println(info.item(j).getTextContent());
									}
								}
								RegUtil.add(item);
							}
						}

					} catch (ParserConfigurationException | SAXException | IOException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (DOMException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					} catch (NBTException e) {
						// TODO 自動生成された catch ブロック
						e.printStackTrace();
					}


					LoadInit = false;
					System.out.println("-------------LOAD END!----------");
				}
			}
		}
	}
}
