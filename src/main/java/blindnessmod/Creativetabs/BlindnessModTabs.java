package blindnessmod.Creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BlindnessModTabs extends CreativeTabs {
	public BlindnessModTabs() {
		super("blindnessmod");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Items.ENDER_EYE);
	}
}
