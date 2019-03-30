package blindnessmod.mixin.renderer;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.client.renderer.RenderGlobal;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

//	@Shadow
//	public Set<TileEntity> setTileEntities;
//
//	@Inject(method = "renderEntities", at = @At("HEAD"), cancellable = true)
//	public void prerenderEntities(Entity renderViewEntity, ICamera camera, float partialTicks,CallbackInfo ci) {
//		setTileEntities.clear();
//		//ci.cancel();
//	}

}
