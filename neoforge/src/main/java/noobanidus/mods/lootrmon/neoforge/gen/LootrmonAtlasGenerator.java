package noobanidus.mods.lootrmon.neoforge.gen;

import com.cobblemon.mod.common.client.render.block.GildedChestBlockRenderer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SpriteSourceProvider;
import noobanidus.mods.lootrmon.common.LootrmonIds;

import java.util.concurrent.CompletableFuture;

public class LootrmonAtlasGenerator extends SpriteSourceProvider {
  public LootrmonAtlasGenerator (PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper helper) {
    super(output, provider, LootrmonIds.MODID, helper);
  }

  @Override
  protected void gather() {
  }
}
