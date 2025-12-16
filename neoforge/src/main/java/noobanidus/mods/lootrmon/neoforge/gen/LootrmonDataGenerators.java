package noobanidus.mods.lootrmon.neoforge.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import noobanidus.mods.lootr.neoforge.gen.*;
import noobanidus.mods.lootrmon.common.LootrmonIds;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid= LootrmonIds.MODID)
public class LootrmonDataGenerators {
  @SubscribeEvent
  public static void gatherData (GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = event.getGenerator().getPackOutput();
    CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();

    LootrmonBlockTagProvider blocks;
    generator.addProvider(event.includeServer(), blocks = new LootrmonBlockTagProvider(output, provider, helper));
    generator.addProvider(event.includeServer(), new LootrmonItemTagProvider(output, provider, blocks.contentsGetter(), helper));
    generator.addProvider(event.includeClient(), new LootrmonAtlasGenerator(output, provider, helper));
    generator.addProvider(event.includeClient(), new LootrmonLangProvider(output));
  }
}
