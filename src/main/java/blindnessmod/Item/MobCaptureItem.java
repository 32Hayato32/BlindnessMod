package blindnessmod.Item;

import blindnessmod.BlindnessMod;
import blindnessmod.init.ItemInit;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;

public class MobCaptureItem extends ItemBase{

	public MobCaptureItem(String name) {
		super(name, BlindnessMod.BlindnessMod);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		System.out.println(target.getDisplayName());
		ItemStack item = new ItemStack(ItemInit.MobCaptureFullItem);
		((MobCaptureFullItem)item.getItem()).setEntity(target);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("Entity", EntityList.getEntityString(target));
		item.setTagCompound(nbt);
		if(playerIn.addItemStackToInventory(item)) {
			target.setDead();
			stack.shrink(1);
		}
		return true;
	}
}
