package blindnessmod.util;

import blindnessmod.BlindnessMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WhiteListUtil {
	public static boolean Check(World w, BlockPos pos, IBlockState s) {
		ItemStack I = s.getBlock().getItem(w, pos, s);
		int id = Item.getIdFromItem(I.getItem());
		int meta = I.getMetadata();
		return BlindnessMod.WhiteBlockList.indexOf(id + ":" + meta) == -1;
		//return false;
	}
}
