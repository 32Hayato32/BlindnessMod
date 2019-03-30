package blindnessmod.mixin.renderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import blindnessmod.util.WhiteListUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

@Mixin(BlockRendererDispatcher.class)
public class MixinBlockRendererDispatcher {

	@Inject(method = "renderBlock", at = @At("HEAD"), cancellable = true)
    public void prerenderBlock(IBlockState state, BlockPos pos, IBlockAccess blockAccess, BufferBuilder bufferBuilderIn,CallbackInfoReturnable<Boolean> ci) {
		//if(state.getBlock().unlocalizedName.indexOf("shulkerBox") == -1) {
			if(WhiteListUtil.Check(Minecraft.getMinecraft().player.getEntityWorld(),pos,state))
				ci.setReturnValue(false);
		//}
	}

	@Inject(method = "renderBlockBrightness", at = @At("HEAD"), cancellable = true)
	public void prerenderBlockBrightness(IBlockState state, float brightness,CallbackInfo ci) {
		if(WhiteListUtil.Check(null,null,state))
			ci.cancel();
	}



}
