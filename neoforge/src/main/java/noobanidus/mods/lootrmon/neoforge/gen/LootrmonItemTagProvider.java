package noobanidus.mods.lootrmon.neoforge.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.neoforge.LootrmonRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LootrmonItemTagProvider extends ItemTagsProvider {
  public LootrmonItemTagProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagsProvider.TagLookup<Block>> blockLookup, @Nullable ExistingFileHelper fileHelper) {
    super(output, provider, blockLookup, LootrmonIds.MODID, fileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider arg) {
    tag(LootrTags.Items.CONTAINERS).add(LootrmonRegistry.GILDED_LOOT_CHEST_ITEM.get());
  }
}
