package noobanidus.mods.lootrmon.neoforge;

import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootr.common.api.registry.LootrProperties;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.common.block.GildedLootChestBlock;
import noobanidus.mods.lootrmon.common.block.entity.GildedLootChestBlockEntity;

public class LootrmonRegistry {
  private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, LootrmonIds.MODID);
  private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, LootrmonIds.MODID);
  private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LootrmonIds.MODID);

  public static final DeferredHolder<Block, GildedLootChestBlock> GILDED_LOOT_CHEST_BLOCK = BLOCKS.register(LootrmonIds.GILDED_CHEST.getPath(), () -> new GildedLootChestBlock(LootrProperties.CHEST_PROPERTIES));
  public static final DeferredHolder<Item, BlockItem> GILDED_LOOT_CHEST_ITEM = ITEMS.register(LootrmonIds.GILDED_CHEST.getPath(), () -> new BlockItem(GILDED_LOOT_CHEST_BLOCK.get(), new Item.Properties()));
  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GildedLootChestBlockEntity>> GILDED_LOOT_CHEST_BLOCK_ENTITY = BLOCK_ENTITIES.register(LootrmonIds.GILDED_CHEST.getPath(), () -> BlockEntityType.Builder.of(GildedLootChestBlockEntity::new, GILDED_LOOT_CHEST_BLOCK.get())
      .build(null));

  public static void register(IEventBus modBus) {
    BLOCKS.register(modBus);
    ITEMS.register(modBus);
    BLOCK_ENTITIES.register(modBus);
    GildedLootChestBlock.type = Suppliers.memoize(GILDED_LOOT_CHEST_BLOCK::get);
    GildedLootChestBlockEntity.type = Suppliers.memoize(GILDED_LOOT_CHEST_BLOCK_ENTITY::get);
  }
}
