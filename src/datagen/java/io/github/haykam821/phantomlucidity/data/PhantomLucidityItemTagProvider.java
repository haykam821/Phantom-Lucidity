package io.github.haykam821.phantomlucidity.data;

import java.util.concurrent.CompletableFuture;

import io.github.haykam821.phantomlucidity.tag.PhantomLucidityItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

public class PhantomLucidityItemTagProvider extends FabricTagProvider.ItemTagProvider {
	public PhantomLucidityItemTagProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registries) {
		super(dataOutput, registries, null);
	}

	@Override
	protected void configure(RegistryWrapper.WrapperLookup lookup) {
		this.getOrCreateTagBuilder(PhantomLucidityItemTags.PHANTOMS_REVEALED_WHILE_USING)
			.add(Items.SPYGLASS);
	}
}
