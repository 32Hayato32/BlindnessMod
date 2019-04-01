package blindnessmod.Tileentity;

import blindnessmod.BlindnessMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileRegBlock extends TileEntity{

	private ItemStackHandler handler = new ItemStackHandler(64);


	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.handler;
		return super.getCapability(capability, facing);
	}

	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public void Reg() {
		if(this.world.isRemote) {
			ItemStack item = this.handler.getStackInSlot(0);
			int id = Item.getIdFromItem(item.getItem());
			int meta = item.getMetadata();
			if(BlindnessMod.WhiteBlockList.indexOf(id + ":" + meta) == -1)
				BlindnessMod.WhiteBlockList.add(id + ":" + meta);
		}
	}
}
