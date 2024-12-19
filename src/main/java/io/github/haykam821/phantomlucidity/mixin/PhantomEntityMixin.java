package io.github.haykam821.phantomlucidity.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

@Mixin(PhantomEntity.class)
public abstract class PhantomEntityMixin extends MobEntity {
	private PhantomEntityMixin(EntityType<? extends MobEntity> type, World world) {
		super(type, world);
	}

	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void initRevealedTrackedData(DataTracker.Builder builder, CallbackInfo ci) {
		builder.add(PhantomLucidity.REVEALED, false);
	}

	@Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
	private void preventUnrevealedAmbientSound(CallbackInfoReturnable<SoundEvent> ci) {
		if (!this.getDataTracker().get(PhantomLucidity.REVEALED)) {
			ci.setReturnValue(null);
		}
	}

	@WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FFZ)V"))
	private boolean preventUnrevealedFlapSound(World world, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) {
		return this.getDataTracker().get(PhantomLucidity.REVEALED);
	}

	@WrapOperation(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/PhantomEntity;setOnFireFor(F)V"))
	private void discardWhenUnrevealedInDaylight(PhantomEntity entity, float seconds, Operation<Void> operation) {
		if (this.getDataTracker().get(PhantomLucidity.REVEALED)) {
			operation.call(entity, seconds);
		} else {
			PhantomLucidity.poof(entity);
			entity.discard();
		}
	}

	@Override
	public boolean shouldDropExperience() {
		return super.shouldDropExperience() && this.getDataTracker().get(PhantomLucidity.REVEALED);
	}

	@Override
	protected boolean shouldDropLoot() {
		return super.shouldDropLoot() && this.getDataTracker().get(PhantomLucidity.REVEALED);
	}

	@Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
	private void readRevealedNbt(NbtCompound nbt, CallbackInfo ci) {
		boolean revealed = nbt.contains(PhantomLucidity.REVEALED_KEY) ? nbt.getBoolean(PhantomLucidity.REVEALED_KEY) : true;
		this.getDataTracker().set(PhantomLucidity.REVEALED, revealed);
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
	private void writeRevealedNbt(NbtCompound nbt, CallbackInfo ci) {
		nbt.putBoolean(PhantomLucidity.REVEALED_KEY, this.getDataTracker().get(PhantomLucidity.REVEALED));
	}
}
