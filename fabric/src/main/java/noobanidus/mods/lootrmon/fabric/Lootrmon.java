package noobanidus.mods.lootrmon.fabric;

import net.fabricmc.api.ModInitializer;

public class Lootrmon implements ModInitializer {
  @Override
  public void onInitialize() {
    LootrmonRegistry.register();
  }
}
