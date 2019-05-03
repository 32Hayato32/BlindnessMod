package blindnessmod.mixin.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import blindnessmod.BlindnessMod;
import blindnessmod.util.WhiteEntityListUtil;
import blindnessmod.util.WhiteListUtil;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

@Mixin(RenderManager.class)
public class MixinRenderEntity{
	private WhiteEntityListUtil EntityList = BlindnessMod.EntityListUtil;
	private WhiteListUtil WhiteUtil = BlindnessMod.WhiteUtil;
	@Inject(method = "renderEntity", at = @At("HEAD"), cancellable = true)
    public void ondoRender(Entity e, double x, double y, double z, float yaw, float partialTicks, boolean p_188391_10_,CallbackInfo ci)
    {
		if(!EntityList.Check(e) && !(e instanceof EntityItem)) {
			ItemStack item = e.getPickedResult(null);
			if(item.getItem() != Items.SPAWN_EGG) {
				if(!WhiteUtil.Check(item)) ci.cancel();
			}else {ci.cancel();}
		}
    }

}
