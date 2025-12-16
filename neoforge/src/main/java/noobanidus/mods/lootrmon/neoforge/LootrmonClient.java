package noobanidus.mods.lootrmon.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.common.client.GildedLootChestBlockRenderer;

@EventBusSubscriber(value= Dist.CLIENT, modid= LootrmonIds.MODID)
public class LootrmonClient {
  @SubscribeEvent
  public static void registerRenderers (EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(LootrmonRegistry.GILDED_LOOT_CHEST_BLOCK_ENTITY.get(), GildedLootChestBlockRenderer::new);
  }
}
