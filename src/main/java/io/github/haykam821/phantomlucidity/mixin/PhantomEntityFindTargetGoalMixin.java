package io.github.haykam821.phantomlucidity.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import net.minecraft.entity.mob.PhantomEntity;

@Mixin(targets = "net.minecraft.entity.mob.PhantomEntity$FindTargetGoal")
public class PhantomEntityFindTargetGoalMixin {
	@SuppressWarnings("target")
	@Shadow(remap = false)
	@Final
	private PhantomEntity field_7319;

	@Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
	private void preventFindingTargetsWhileNotRevealed(CallbackInfoReturnable<Boolean> ci) {
		boolean revealed = this.field_7319.getDataTracker().get(PhantomLucidity.REVEALED);

		if (!revealed) {
			ci.setReturnValue(false);
		}
	}
}
