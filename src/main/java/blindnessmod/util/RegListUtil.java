package blindnessmod.util;

import blindnessmod.BlindnessMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RegListUtil {

	public static void add(String key,int count) {
		BlindnessMod.RegBlockList.put(key,count+":"+0);
	}

	public static ItemStack getItem(int Index) {
		String s = (String) BlindnessMod.RegBlockList.keySet().toArray()[Index];
		ItemStack item = new ItemStack(Item.getItemById(Integer.parseInt(s.split(":")[0])));
		item.setItemDamage(Integer.parseInt(s.split(":")[1]));
		return item;
	}

	public static int getCount(ItemStack item) {
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		String key = id + ":" + meta;
		return Integer.parseInt(BlindnessMod.RegBlockList.get(key).split(":")[0]);
	}

	public static float getDisCount(ItemStack item) {
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		String key = id + ":" + meta;
		return Float.parseFloat(BlindnessMod.RegBlockList.get(key).split(":")[1]);
	}
}
