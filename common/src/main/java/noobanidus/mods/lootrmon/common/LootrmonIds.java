package noobanidus.mods.lootrmon.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import noobanidus.mods.lootr.common.api.LootrConstants;

public class LootrmonIds {
  public static final String MODID = "lootrmon";

  public static ResourceLocation rl (String path) {
    return ResourceLocation.fromNamespaceAndPath(MODID, path);
  }

  public static ResourceLocation crl (String path) {
    return ResourceLocation.fromNamespaceAndPath("cobblemon", path);
  }

  public static final ResourceLocation GILDED_CHEST = rl("gilded_chest");

  public static final BlockBehaviour.Properties GILDED_CHEST_PROPERTIES = LootrConstants.CHEST_PROPERTIES;
}
