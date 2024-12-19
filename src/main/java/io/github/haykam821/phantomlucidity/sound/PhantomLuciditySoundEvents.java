package io.github.haykam821.phantomlucidity.sound;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public final class PhantomLuciditySoundEvents {
	public static final SoundEvent ENTITY_PHANTOM_FADE = of("entity.phantom.fade");

	private PhantomLuciditySoundEvents() {
		return;
	}

	public static void register() {
		Registry.register(Registries.SOUND_EVENT, ENTITY_PHANTOM_FADE.id(), ENTITY_PHANTOM_FADE);
	}

	private static SoundEvent of(String path) {
		return SoundEvent.of(PhantomLucidity.identifier(path));
	}
}
