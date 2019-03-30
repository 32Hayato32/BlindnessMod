package blindnessmod.Block.Containers;

import blindnessmod.Tileentity.TileRegBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerRegBlock extends Container{

	private final TileRegBlock tile;

	public ContainerRegBlock(InventoryPlayer p, TileRegBlock t) {

		this.tile = t;
//		IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
//
//		this.addSlotToContainer(new SlotItemHandler(handler, 0, 26, 11));

		//Inventory
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(p, x + y*9 + 9, 8 + x*18, 120 + y*18));
			}
		}

		//HotBar
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(p, x, 8 + x * 18, 178));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer p) {
		// TODO 自動生成されたメソッド・スタブ
		return this.tile.isUsableByPlayer(p);
	}

}
