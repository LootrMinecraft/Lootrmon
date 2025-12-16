package noobanidus.mods.lootrmon.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import noobanidus.mods.lootrmon.common.client.GildedLootChestBlockRenderer;

public class LootrmonClient implements ClientModInitializer {
  @Override
  public void onInitializeClient() {
    BlockEntityRenderers.register(LootrmonRegistry.GILDED_LOOT_CHEST_BLOCK_ENTITY, GildedLootChestBlockRenderer::new);
  }
}
