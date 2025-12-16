package noobanidus.mods.lootrmon.common.impl.replacement;

import com.google.auto.service.AutoService;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import noobanidus.mods.lootr.common.api.replacement.ILootrBlockReplacementProvider;
import noobanidus.mods.lootrmon.common.LootrmonTags;
import noobanidus.mods.lootrmon.common.block.GildedLootChestBlock;

@AutoService(ILootrBlockReplacementProvider.class)
public class GildedChestReplacementProvider implements ILootrBlockReplacementProvider {
  @Override
  public TagKey<Block> getApplicableTag() {
    return LootrmonTags.CONVERT_GILDED;
  }

  @Override
  public Block getBlock() {
    return GildedLootChestBlock.type.get();
  }
}
