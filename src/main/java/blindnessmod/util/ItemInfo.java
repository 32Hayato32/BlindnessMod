package blindnessmod.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemInfo {
	public int ID;
	public int META;

	public int Count;
	public float DisCount;

	public boolean HasNBT;
	public NBTTagCompound NBT;

	public ItemInfo(ItemStack item , int cnt , float dis ) {
		ID = Item.getIdFromItem(item.getItem());
		META = item.getMetadata();
		Count = cnt;
		DisCount = dis;

		HasNBT = item.hasTagCompound();
		if(HasNBT) {
			NBT = item.getTagCompound();
		}
	}

	public ItemStack getItem() {
		ItemStack i = new ItemStack(Item.getItemById(ID));
		i.setItemDamage(META);
		i.setTagCompound(NBT);
		return i;
	}

	@Override
	public boolean equals(Object obj) {
		ItemInfo o = (ItemInfo)obj;
		boolean f = this.ID == o.ID && this.META == o.META && this.HasNBT == o.HasNBT;
		if(f && o.HasNBT && this.NBT != null && o.NBT != null) {
			f = o.NBT.equals(this.NBT);
		}
		return f;
	}

}
