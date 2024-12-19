package io.github.haykam821.phantomlucidity.tag;

import io.github.haykam821.phantomlucidity.PhantomLucidity;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public final class PhantomLucidityItemTags {
	public static final TagKey<Item> PHANTOMS_REVEALED_WHILE_USING = of("phantoms_revealed_while_using");

	private PhantomLucidityItemTags() {
		return;
	}

	private static TagKey<Item> of(String path) {
		return TagKey.of(RegistryKeys.ITEM, PhantomLucidity.identifier(path));
	}
}
