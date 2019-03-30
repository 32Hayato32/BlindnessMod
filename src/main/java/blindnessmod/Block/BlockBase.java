package blindnessmod.Block;

import blindnessmod.BlindnessMod;
import blindnessmod.init.BlockInit;
import blindnessmod.init.ItemInit;
import blindnessmod.util.interfaces.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class BlockBase extends Block implements IHasModel{
	public BlockBase(String name, Material material, CreativeTabs tab)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tab);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(name));
	}

	@Override
	public void registerModels()
	{
		BlindnessMod.proxy.registerModel(Item.getItemFromBlock(this), 0);
	}
}