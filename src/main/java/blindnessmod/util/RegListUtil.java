package blindnessmod.util;

import blindnessmod.BlindnessMod;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RegListUtil {

	public static void add(String key,int count) {
		BlindnessMod.RegBlockList.put(key,count+":"+0);
	}

	public static void add(ItemInfo info) {
		if(!BlindnessMod.RegItemList.contains(info)) {
			System.out.println("addFunc : " + info.ID);
			BlindnessMod.RegItemList.add(info);
		}else {
			int idx = getIndex(info);
			System.out.println("updateFunc : " + info.ID);
			ItemInfo baf = BlindnessMod.RegItemList.get(idx);
			ItemStack item = baf.getItem();
			BlindnessMod.RegItemList.set(idx, new ItemInfo(item,baf.Count + info.Count,baf.DisCount));
		}
	}

	public static int getIndex(ItemInfo info) {
		return BlindnessMod.RegItemList.indexOf(info);
	}

	public static void update(String key,int count) {
		BlindnessMod.RegBlockList.put(key,count+":"+0);
	}

	public static void update(String key,int count,float discount) {
		BlindnessMod.RegBlockList.put(key,count+":"+discount);
	}

	public static void update(int index,int count,float discount) {
		BlindnessMod.RegItemList.get(index).Count = count;
		BlindnessMod.RegItemList.get(index).DisCount = discount;
	}

	public static ItemStack getItem(int Index) {
//		String s = (String) BlindnessMod.RegBlockList.keySet().toArray()[Index];
//		ItemStack item = new ItemStack(Item.getItemById(Integer.parseInt(s.split(":")[0])));
//		item.setItemDamage(Integer.parseInt(s.split(":")[1]));

		return BlindnessMod.RegItemList.get(Index).getItem();
	}

	public static ItemStack getTransItem(int Index) {
		ItemStack item = getItem(Index);
		int StackLimit = item.getMaxStackSize();
		if(getCount(Index) >= StackLimit) {
			item.setCount(StackLimit);
		}else {
			item.setCount(getCount(Index));
		}
		return item;
	}

	public static void DicItemStackCount(int index,int count) {
		ItemStack item = getItem(index);
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		int bufCnt = getCount(item);
		float bufDic = getDisCount(item);
		String key = id + ":" + meta;
//		BlindnessMod.RegBlockList.put(key, bufCnt - count + ":" + bufDic);
		BlindnessMod.RegItemList.get(index).Count  -= count;
	}

	public static int getCount(ItemStack item) {
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		String key = id + ":" + meta;
		return Integer.parseInt(BlindnessMod.RegBlockList.get(key).split(":")[0]);
	}

	public static int getCount(int Index) {
		return BlindnessMod.RegItemList.get(Index).Count;
	}

	public static float getDisCount(ItemStack item) {
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		String key = id + ":" + meta;
		return Float.parseFloat(BlindnessMod.RegBlockList.get(key).split(":")[1]);
	}

	public static float getDisCount(int Index) {
		return BlindnessMod.RegItemList.get(Index).DisCount;
	}

	public static int getGoalValue(ItemStack item) {
		int GoalCount = 3456 - (int)((3456.0 / 100.0) * ( getDisCount(item)));
		if(GoalCount == 0)GoalCount = 2;
		return GoalCount;
	}

	public static int getGoalValue(int Index) {
		int GoalCount = 3456 - (int)((3456.0 / 100.0) * ( getDisCount(Index)));
		if(GoalCount == 0)GoalCount = 2;
		return GoalCount;
	}

	public static int getLeftValue(ItemStack item) {
		return getGoalValue(item) - getCount(item);
	}

	public static int getLeftValue(int Index) {
		return getGoalValue(Index) - getCount(Index);
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
