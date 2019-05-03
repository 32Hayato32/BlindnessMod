package blindnessmod.util;

import blindnessmod.BlindnessMod;
import blindnessmod.Item.MobCaptureFullItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WhiteListUtil {
	public static boolean Check(World w, BlockPos pos, IBlockState s) {
		ItemStack I = s.getBlock().getItem(w, pos, s);
		int id = Item.getIdFromItem(I.getItem());
		int meta = I.getMetadata();
//		return BlindnessMod.WhiteBlockList.indexOf(id + ":" + meta) == -1;
		if(!(BlindnessMod.WhiteBlockList.indexOf(id + ":" + meta) == -1)) {
			return false;
		}else {
			return BlindnessMod.SpecialWhiteBlockList.indexOf(s.getBlock().getRegistryName().toString()) == -1;
		}
		//return false;
	}

	public static boolean Check(ItemStack item) {
		ItemStack I = item;
		int id = Item.getIdFromItem(I.getItem());
		int meta = I.getMetadata();
		return !(BlindnessMod.WhiteBlockList.indexOf(id + ":" + meta) == -1);
//		if(!(BlindnessMod.WhiteBlockList.indexOf(id + ":" + meta) == -1)) {
//			return false;
//		}
	}

	public static boolean hasItem(ItemStack item) {
		int id = Item.getIdFromItem(item.getItem());
		int meta = item.getMetadata();
		String key = id + ":" + meta;
		if(item.getItem() instanceof MobCaptureFullItem) {
			MobCaptureFullItem i  = (MobCaptureFullItem)item.getItem();
			NBTTagCompound nbt = item.getTagCompound();
			System.out.println(nbt.getString("Entity"));
			return BlindnessMod.EntityListUtil.Check(nbt.getString("Entity"));
		}
		return BlindnessMod.WhiteBlockList.indexOf(key) > -1;
	}

	public static boolean hasItem(String key) {
		return BlindnessMod.WhiteBlockList.indexOf(key) > -1;
	}

	public static void Reg(ItemStack item) {
		if(!hasItem(item)) {
			int id = Item.getIdFromItem(item.getItem());
			int meta = item.getMetadata();
			String key = id + ":" + meta;
			BlindnessMod.WhiteBlockList.add(key);
		}
	}

	public static void Reg(String key) {
		if(!hasItem(key)) {
			BlindnessMod.WhiteBlockList.add(key);
		}
	}
}
