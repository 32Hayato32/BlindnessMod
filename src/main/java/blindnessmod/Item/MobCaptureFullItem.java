package blindnessmod.Item;

import java.util.List;

import blindnessmod.BlindnessMod;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MobCaptureFullItem extends ItemBase {

	private Entity ent;
	private String name ;

	public MobCaptureFullItem(String name) {
		super(name, BlindnessMod.BlindnessMod);
	}

	@Override
	public int getItemStackLimit() {
		return 1;
	}

	@Override
	public boolean hasEffect(ItemStack stack) {
		return true;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			String str = nbt.getString("Entity");
			return str + " Soul";
		}
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(stack.hasTagCompound()) {
			NBTTagCompound nbt = stack.getTagCompound();
			String str = nbt.getString("Entity");
            tooltip.add(TextFormatting.GRAY + str);
		}
	}

	public void setEntity(Entity e) {
		this.ent = e;
	}

	public String getEntityName() {
		return name;
	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt) {
		this.name = nbt.getString("Entity");
		return super.updateItemStackNBT(nbt);
	}

}
