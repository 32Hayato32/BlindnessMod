package blindnessmod.util.Handlers;

import blindnessmod.Reference;
import blindnessmod.Block.Containers.ContainerRegBlock;
import blindnessmod.Block.Gui.RegBlockGui;
import blindnessmod.Tileentity.TileRegBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer p, World w, int x, int y, int z) {
		if(ID == Reference.GUI_REGISTER_BLOCK) return new RegBlockGui(p.inventory, (TileRegBlock)w.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer p, World w, int x, int y, int z) {
		if(ID == Reference.GUI_REGISTER_BLOCK) return new ContainerRegBlock(p.inventory, (TileRegBlock)w.getTileEntity(new BlockPos(x,y,z)));
		return null;
	}
}
