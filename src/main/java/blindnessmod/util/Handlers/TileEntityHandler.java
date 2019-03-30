package blindnessmod.util.Handlers;

import blindnessmod.Reference;
import blindnessmod.Tileentity.TileRegBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {
	public static void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileRegBlock.class, new ResourceLocation(Reference.MODID + ":register_block").toString());
	}
}
