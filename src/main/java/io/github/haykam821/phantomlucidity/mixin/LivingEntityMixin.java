package io.github.haykam821.phantomlucidity.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import io.github.haykam821.phantomlucidity.tag.PhantomLucidityItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	private LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Inject(method = "tickItemStackUsage", at = @At("HEAD"))
	private void tickRevealPhantomsUsingSpyglass(ItemStack stack, CallbackInfo ci) {
		if (!this.getWorld().isClient() && stack.isIn(PhantomLucidityItemTags.PHANTOMS_REVEALED_WHILE_USING)) {
			PhantomLucidity.tryRevealPhantom((LivingEntity) (Object) this);
		}
	}
}
