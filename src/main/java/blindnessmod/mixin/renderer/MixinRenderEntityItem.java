package blindnessmod.mixin.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import blindnessmod.BlindnessMod;
import blindnessmod.util.WhiteListUtil;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.entity.item.EntityItem;

@Mixin(RenderEntityItem.class)
public class MixinRenderEntityItem {
	private WhiteListUtil WhiteUtil = BlindnessMod.WhiteUtil;

	@Inject(method = "doRender", at = @At("HEAD"), cancellable = true)
    public void ondoRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks,CallbackInfo ci)
    {
		if(!WhiteUtil.Check(entity.getItem())) {
			ci.cancel();
		}
    }

}
