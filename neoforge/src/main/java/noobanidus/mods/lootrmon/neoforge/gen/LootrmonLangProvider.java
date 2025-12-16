package noobanidus.mods.lootrmon.neoforge.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.common.LootrmonTags;
import noobanidus.mods.lootrmon.neoforge.LootrmonRegistry;

public class LootrmonLangProvider extends LanguageProvider {

  public LootrmonLangProvider (PackOutput output) {
    super(output, LootrmonIds.MODID, "en_us");
  }
  @Override
  protected void addTranslations() {
    add(LootrmonRegistry.GILDED_LOOT_CHEST_BLOCK.get(), "Gilded Loot Chest");

    add(LootrmonTags.CONVERT_GILDED, "Lootrmon: Convert to Gilded Loot Chest");
  }
}
