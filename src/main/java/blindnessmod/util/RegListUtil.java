package blindnessmod.util;

import blindnessmod.BlindnessMod;
import net.minecraft.init.Items;
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

	public static int getGoalValue(ItemStack item) {
		int GoalCount = 3456 - (int)((3456.0 / 100.0) * ( getDisCount(item)));
		if(GoalCount == 0)GoalCount = 2;
		return GoalCount;
	}

	public static int getLeftValue(ItemStack item) {
		return getGoalValue(item) - getCount(item);
	}

	public static float getRate(Item item) {
		float value = 0.0f;
		if(item == Items.IRON_INGOT)value =  0.3125f;
		if(item == Items.GOLD_INGOT)value = 0.625f;
		if(item == Items.EMERALD)value = 1.25f;
		if(item == Items.DIAMOND)value = 1.5625f;
		return value;
	}
}
