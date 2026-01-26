package noobanidus.mods.lootrmon.fabric;

import com.google.common.base.Suppliers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.common.block.GildedLootChestBlock;
import noobanidus.mods.lootrmon.common.block.entity.GildedLootChestBlockEntity;

public class LootrmonRegistry {
  public static final GildedLootChestBlock GILDED_LOOT_CHEST = new GildedLootChestBlock(LootrmonIds.GILDED_CHEST_PROPERTIES);

  public static final BlockItem GILDED_LOOT_CHEST_ITEM = new BlockItem(GILDED_LOOT_CHEST, new Item.Properties());

  public static final BlockEntityType<GildedLootChestBlockEntity> GILDED_LOOT_CHEST_BLOCK_ENTITY = BlockEntityType.Builder.of(GildedLootChestBlockEntity::new, GILDED_LOOT_CHEST).build(null);

  public static void register () {
    Registry.register(BuiltInRegistries.BLOCK, LootrmonIds.GILDED_CHEST, GILDED_LOOT_CHEST);
    Registry.register(BuiltInRegistries.ITEM, LootrmonIds.GILDED_CHEST, GILDED_LOOT_CHEST_ITEM);
    Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, LootrmonIds.GILDED_CHEST, GILDED_LOOT_CHEST_BLOCK_ENTITY);
    GildedLootChestBlock.type = Suppliers.memoize(() -> GILDED_LOOT_CHEST);
    GildedLootChestBlockEntity.type = Suppliers.memoize(() -> GILDED_LOOT_CHEST_BLOCK_ENTITY);
  }

}
