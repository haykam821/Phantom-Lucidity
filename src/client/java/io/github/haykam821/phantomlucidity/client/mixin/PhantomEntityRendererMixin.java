package io.github.haykam821.phantomlucidity.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import net.minecraft.client.render.entity.PhantomEntityRenderer;
import net.minecraft.client.render.entity.state.PhantomEntityRenderState;
import net.minecraft.entity.mob.PhantomEntity;

@Mixin(PhantomEntityRenderer.class)
public class PhantomEntityRendererMixin {
	@Inject(method = "updateRenderState", at = @At("TAIL"))
	private void updateRevealedRenderState(PhantomEntity entity, PhantomEntityRenderState renderState, float tickDelta, CallbackInfo ci) {
		boolean revealed = entity.getDataTracker().get(PhantomLucidity.REVEALED);

		if (!revealed) {
			if (!renderState.invisible) {
				renderState.invisibleToPlayer = false;
			}

			renderState.invisible = true;
		}
	}
}
