package noobanidus.mods.lootrmon.common.impl.type;

import com.cobblemon.mod.common.CobblemonBlocks;
import com.google.auto.service.AutoService;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import noobanidus.mods.lootr.common.api.ILootrType;
import org.jetbrains.annotations.Nullable;

@AutoService(ILootrType.class)
public class GildedChestType implements ILootrType {
  public static GildedChestType type;

  @Override
  public String getName() {
    return "gilded_chest";
  }

  @Override
  public @Nullable Block getReplacementBlock() {
    return CobblemonBlocks.GILDED_CHEST;
  }

  @Override
  public @Nullable EntityType<?> getReplacementEntity() {
    return null;
  }

  @Override
  public void callback() {
    type = this;
  }
}
