package noobanidus.mods.lootrmon.neoforge.gen;

import com.cobblemon.mod.common.CobblemonBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.common.LootrmonTags;
import noobanidus.mods.lootrmon.common.block.GildedLootChestBlock;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class LootrmonBlockTagProvider extends BlockTagsProvider {
  public LootrmonBlockTagProvider (PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
    super(output, lookupProvider, LootrmonIds.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider arg) {
    tag(LootrmonTags.CONVERT_GILDED).add(
        CobblemonBlocks.GILDED_CHEST,
        CobblemonBlocks.GREEN_GILDED_CHEST,
        CobblemonBlocks.BLUE_GILDED_CHEST,
        CobblemonBlocks.WHITE_GILDED_CHEST,
        CobblemonBlocks.YELLOW_GILDED_CHEST,
        CobblemonBlocks.PINK_GILDED_CHEST,
        CobblemonBlocks.BLACK_GILDED_CHEST);
    tag(LootrTags.Blocks.CONTAINERS).add(GildedLootChestBlock.type.get());
    tag(LootrTags.Blocks.INTERACT_WHITELIST_BLOCKS).add(GildedLootChestBlock.type.get());
    tag(LootrTags.Blocks.CONVERT_BLOCK).addTag(LootrmonTags.CONVERT_GILDED);
  }
}
