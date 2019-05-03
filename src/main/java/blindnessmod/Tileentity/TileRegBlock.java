package blindnessmod.Tileentity;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import blindnessmod.BlindnessMod;
import blindnessmod.Item.MobCaptureFullItem;
import blindnessmod.util.ItemInfo;
import blindnessmod.util.RegListUtil;
import blindnessmod.util.SpecialWhiteListUtil;
import blindnessmod.util.WhiteEntityListUtil;
import blindnessmod.util.WhiteListUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileRegBlock extends TileEntity implements ITickable,IInventory{

	private RegListUtil RegUtil = BlindnessMod.RegUtil;
	private WhiteListUtil WhiteUrtil = BlindnessMod.WhiteUtil;
	private SpecialWhiteListUtil SpecialUtil = BlindnessMod.SpecialUtil;
	private WhiteEntityListUtil EntityListUtil = BlindnessMod.EntityListUtil;

	private ItemStackHandler handler = new ItemStackHandler(64);
	private IItemHandler hand = new InvWrapper(this);
    private NonNullList<ItemStack> ItemStacks = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
    private int flag = 0;
    private int index = -1;
	private String CustomName;
	private EntityPlayer p;

	private EnumFacing[] Facings = {EnumFacing.UP,EnumFacing.DOWN,EnumFacing.EAST,EnumFacing.NORTH,EnumFacing.SOUTH,EnumFacing.WEST};

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
		if(!BlindnessMod.RegBlockList.containsKey(str)) {
			RegUtil.add(str,count);
		}else {
			int bafCnt =RegUtil.getCount(item);
			float bafDiscnt = RegUtil.getDisCount(item);
			BlindnessMod.RegBlockList.put(str,bafCnt + count + ":" + bafDiscnt);
		}
		RegUtil.add(new ItemInfo(item,item.getCount(),item.getMetadata()));
		System.out.println(id + " is Reg!");
		return true;
	}

	public boolean RegSideBlock() {
		boolean f = false;
		if(world.isRemote) {
			List<IBlockState> list = new ArrayList<IBlockState>();
			IBlockState b = null;
			for(EnumFacing face : Facings) {
				b = this.world.getBlockState(this.pos.offset(face));
				if(b.getBlock() != Blocks.AIR)list.add(b);
				System.out.println(b.getBlock().getRegistryName());
			}

			ItemStack item = this.ItemStacks.get(0);
			if(!(item.getItem() == Items.DIAMOND && item.getCount() == 64)) {
				return false;
			}else if(item.getItem() == Items.IRON_INGOT) {
				for(IBlockState i:list) {
					System.out.println(i.getBlock().getRegistryName());
					p.sendMessage(new TextComponentTranslation(i.getBlock().getRegistryName().toString()));
				}
				return false;
			}

			if(list.size() == 1) {
				String key = list.get(0).getBlock().getRegistryName().toString();
				if(!SpecialUtil.hasBlock(key)) {
					SpecialUtil.Reg(key);
					f =  true;
				}
			}else {
				f = false;
			}
		}
		return f;
	}

	public void WhiteListReg() {
		if(this.getIndex() != -1) {
			ItemStack item = RegUtil.getItem(this.getIndex());
			int id = Item.getIdFromItem(item.getItem());
			int meta = item.getMetadata();
			String key = id + ":" + meta;
			if(!WhiteUrtil.hasItem(item)) {
				System.out.println("TileSide WhiteReg Start :" + item.getDisplayName());
				WhiteUrtil.Reg(key);
				if(item.getItem() instanceof MobCaptureFullItem) {
					MobCaptureFullItem i  = (MobCaptureFullItem)item.getItem();
					NBTTagCompound nbt = item.getTagCompound();
					EntityListUtil.Reg(nbt.getString("Entity"));
				}
				RegUtil.update(this.getIndex(),0,100.0f);
			}
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
			int bafCnt =RegUtil.getCount(index);
			float bafDiscnt = RegUtil.getDisCount(index);
			float rdc = (bafDiscnt + Discnt) >= 100.0?100.0f:bafDiscnt + Discnt;
			BlindnessMod.RegItemList.get(index).DisCount = rdc;
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

	public void setIndex(int index) {
		this.index = index;
	}

	public void setPlayer(EntityPlayer _p) {
		this.p = _p;
	}

	public int getFlag() {
		return this.flag;
	}

	public int getIndex() {
		return this.index;
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
			case 3:
				this.TransItem();
				this.setFlag(0);
				this.setIndex(-1);
				break;
			case 4:
				this.WhiteListReg();
				this.setFlag(0);
				this.setIndex(-1);
				break;
			case 5:
				if(this.RegSideBlock())this.ItemStacks.set(0,ItemStack.EMPTY);
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

	private void TransItem() {
		if(!world.isRemote) {
			int ix = this.getIndex();
			if(ix != -1) {
				ItemStack item = RegUtil.getTransItem(ix);
				System.out.println(item.getCount() + " : " + world.isRemote);
				int cnt = item.getCount();
				if(p.addItemStackToInventory(item)) {
					RegUtil.DicItemStackCount(ix,cnt);
				}
			}
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
