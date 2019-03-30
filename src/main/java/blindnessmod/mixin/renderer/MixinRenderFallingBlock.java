package blindnessmod.mixin.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import blindnessmod.util.WhiteListUtil;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.math.BlockPos;

@Mixin(RenderFallingBlock.class)
public class MixinRenderFallingBlock {

	@Inject(method = "doRender",at = @At("HEAD"),cancellable = true)
	public void postDoRender(EntityFallingBlock entity, double x, double y, double z, float entityYaw, float partialTicks,CallbackInfo ci) {
		if(entity.getBlock() != null)
			if(WhiteListUtil.Check(entity.getWorldObj(), new BlockPos(entity.posX, entity.getEntityBoundingBox().maxY, entity.posZ), entity.getBlock()))
				ci.cancel();
	}

}
