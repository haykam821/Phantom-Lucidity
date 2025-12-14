package io.github.haykam821.phantomlucidity.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;

@Mixin(MobEntity.class)
public class MobEntityMixin {
	@WrapOperation(method = "tickBurnInDaylight", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;setOnFireFor(F)V"))
	private void discardWhenUnrevealedInDaylight(MobEntity entity, float seconds, Operation<Void> operation) {
		if (!(entity instanceof PhantomEntity) || entity.getDataTracker().get(PhantomLucidity.REVEALED)) {
			operation.call(entity, seconds);
		} else {
			PhantomLucidity.poof(entity);
			entity.discard();
		}
	}
}
