package blindnessmod.proxy;

import blindnessmod.BlindnessMod;
import blindnessmod.Reference;
import blindnessmod.Entitys.TESTEntity;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
    	EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID , "TEST"), TESTEntity.class, "TEST", 0, BlindnessMod.INSTANCE, 120, 1,false);
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    public void registerCommands(MinecraftServer server) {
    }

    public void registerModel(Item item, int metadata) {}
}
