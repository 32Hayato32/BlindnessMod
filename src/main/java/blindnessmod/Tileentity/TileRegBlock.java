package blindnessmod.Tileentity;

import javax.annotation.Nullable;

import blindnessmod.BlindnessMod;
import blindnessmod.util.RegListUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileRegBlock extends TileEntity implements ITickable,IInventory{

	private RegListUtil RegUtil = new RegListUtil();
	private ItemStackHandler handler = new ItemStackHandler(64);
	private IItemHandler hand = new InvWrapper(this);
    private NonNullList<ItemStack> ItemStacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
    private int flag = 0;
	private String CustomName;

    @Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) this.hand;
		return super.getCapability(capability, facing);
	}

	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	public boolean Reg() {
		ItemStack item = this.ItemStacks.get(0);
		if(item.getItem() == Items.AIR)return false;
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		int count = item.getCount();
		String str = id + ":" + meta;
		if(BlindnessMod.WhiteBlockList.indexOf(str) == -1) {
			if(!BlindnessMod.RegBlockList.containsKey(str)) {
				RegUtil.add(str,count);
			}else {
				int bafCnt =RegUtil.getCount(item);
				float bafDiscnt = RegUtil.getDisCount(item);
				BlindnessMod.RegBlockList.put(str,bafCnt + count + ":" + bafDiscnt);
			}
			System.out.println("Reg!");
			return true;
		}else
		{
			return false;
		}
	}

	public boolean addOsonae(int index) {
		ItemStack item = RegUtil.getItem(index);
		ItemStack initem = this.ItemStacks.get(0);
		Item i = initem.getItem();
		if(i == Items.IRON_INGOT || i == Items.GOLD_INGOT || i == Items.EMERALD || i == Items.DIAMOND) {
			int id = Item.getIdFromItem(item.getItem());
			int meta = item.getMetadata();
			float Discnt = RegUtil.getRate(i) * initem.getCount();
			System.out.println(Discnt);
			String str = id + ":" + meta;
			int bafCnt =RegUtil.getCount(item);
			float bafDiscnt = RegUtil.getDisCount(item);
			float rdc = (bafDiscnt + Discnt) >= 100.0?100.0f:bafDiscnt + Discnt;
			BlindnessMod.RegBlockList.put(str,bafCnt + ":" + rdc);
			System.out.println("add O★S★O★N★A★E!");
			return true;
		}else{
			return false;
		}
	}

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, this.getUpdateTag());
    }


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("Flag", this.flag);
		return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.flag = compound.getInteger("Flag");
	}

	public void setFlag(int i) {
		this.flag = i;
	}

	public int getFlag() {
		return this.flag;
	}

	@Override
	public void update() {
		switch(this.getFlag()) {
			case 1:
				this.ItemStacks.set(0, ItemStack.EMPTY);
				this.setFlag(0);
				break;
			case 2:
				Item item = ItemStacks.get(0).getItem();
				if(item == Items.IRON_INGOT || item == Items.GOLD_INGOT || item == Items.EMERALD || item == Items.DIAMOND)
					this.ItemStacks.set(0,ItemStack.EMPTY);
				this.setFlag(0);
				break;
		}
	}

	public void setCustomName(String customName) {
		CustomName = customName;
	}

	@Override
	public String getName() {
		return this.hasCustomName()?this.CustomName : "container.regblock";
	}

	@Override
	public boolean hasCustomName() {
		return this.CustomName != null && !this.CustomName.isEmpty();
	}

	@Override
	public NBTTagCompound getUpdateTag() {
	    return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public int getSizeInventory() {
		return this.ItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack item : this.ItemStacks) {
			if(!item.isEmpty())
				return false;
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.ItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(ItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(ItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        ItemStack itemstack = this.ItemStacks.get(index);
        boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
        this.ItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit())
        {
            stack.setCount(this.getInventoryStackLimit());
        }

        if (index == 0 && !flag)
        {
            this.markDirty();
        }
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO 自動生成されたメソッド・スタブ
		return 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return true;
	}

	@Override
	public int getField(int id) {
		return (id == 0)?this.flag:0;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0)this.flag = value;
	}

	@Override
	public int getFieldCount() {
		return 1;
	}

	@Override
	public void clear() {
	}
}
