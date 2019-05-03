package blindnessmod.mixin.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import blindnessmod.util.WhiteListUtil;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;

@Mixin(TileEntityRendererDispatcher.class)
public class MixinTileEntityRendererDispatcher {

	@Inject(method = "render", at = @At("HEAD"), cancellable = true)
	public void prerender(TileEntity tileentityIn, float partialTicks, int destroyStage,CallbackInfo ci) {
		//System.out.println(tileentityIn.getBlockType());
		if(WhiteListUtil.Check(tileentityIn.getWorld(),tileentityIn.getPos(), tileentityIn.getBlockType().getDefaultState()))
			ci.cancel();
	}
}
