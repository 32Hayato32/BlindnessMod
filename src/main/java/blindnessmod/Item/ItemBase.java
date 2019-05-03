package blindnessmod.Item;

import blindnessmod.BlindnessMod;
import blindnessmod.init.ItemInit;
import blindnessmod.util.interfaces.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemBase extends Item implements IHasModel{
	public ItemBase(String name,CreativeTabs tab) {
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(tab);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		BlindnessMod.proxy.registerModel(this,0);
	}

}
