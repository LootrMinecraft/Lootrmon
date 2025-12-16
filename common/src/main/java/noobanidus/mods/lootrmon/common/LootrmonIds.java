package noobanidus.mods.lootrmon.common;

import net.minecraft.resources.ResourceLocation;

public class LootrmonIds {
  public static final String MODID = "lootrmon";

  public static ResourceLocation rl (String path) {
    return ResourceLocation.fromNamespaceAndPath(MODID, path);
  }

  public static ResourceLocation crl (String path) {
    return ResourceLocation.fromNamespaceAndPath("cobblemon", path);
  }

  public static final ResourceLocation GILDED_CHEST = rl("gilded_chest");
}
