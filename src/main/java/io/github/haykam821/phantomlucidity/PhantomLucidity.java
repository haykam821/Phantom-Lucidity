package io.github.haykam821.phantomlucidity;

import io.github.haykam821.phantomlucidity.sound.PhantomLuciditySoundEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PhantomEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;

public class PhantomLucidity implements ModInitializer {
	private static final String MOD_ID = "phantomlucidity";

	public static final String REVEALED_KEY = PhantomLucidity.identifier("revealed").toString();
	public static final TrackedData<Boolean> REVEALED = DataTracker.registerData(PhantomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	@Override
	public void onInitialize() {
		PhantomLuciditySoundEvents.register();
	}

	private static boolean isRevealable(Entity entity) {
		return !entity.isSpectator();
	}

	public static void poof(LivingEntity entity) {
		entity.getWorld().sendEntityStatus(entity, EntityStatuses.ADD_DEATH_PARTICLES);
		entity.playSound(PhantomLuciditySoundEvents.ENTITY_PHANTOM_FADE);
		entity.emitGameEvent(GameEvent.ENTITY_ACTION);
	}

	public static void tryRevealPhantom(LivingEntity entity) {
		Vec3d rotation = entity.getRotationVec(1);

		Vec3d startPos = entity.getEyePos();
		Vec3d endPos = startPos.add(rotation.x * 100, rotation.y * 100, rotation.z * 100);

		Box box = new Box(startPos, endPos).expand(1);

		EntityHitResult hitResult = ProjectileUtil.getEntityCollision(entity.getWorld(), entity, startPos, endPos, box, PhantomLucidity::isRevealable, 0);
		if (hitResult == null) return;

		Entity target = hitResult.getEntity();
		if (!entity.canSee(target)) return;

		if (target instanceof PhantomEntity phantom && !phantom.getDataTracker().get(REVEALED)) {
			phantom.getDataTracker().set(REVEALED, true);
			PhantomLucidity.poof(phantom);
		}
	}

	public static Identifier identifier(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
