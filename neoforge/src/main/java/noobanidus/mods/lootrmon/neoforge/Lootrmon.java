package noobanidus.mods.lootrmon.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import noobanidus.mods.lootrmon.common.LootrmonIds;

@Mod(LootrmonIds.MODID)
public class Lootrmon {
  public Lootrmon(ModContainer container, IEventBus modBus) {
    LootrmonRegistry.register(modBus);
  }
}
