package noobanidus.mods.lootrmon.common.block.entity;

import com.cobblemon.mod.common.CobblemonSounds;
import com.cobblemon.mod.common.block.chest.GildedState;
import com.google.auto.service.AutoService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import noobanidus.mods.lootr.common.api.ILootrBlockEntityConverter;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.LootrBlockType;
import noobanidus.mods.lootr.common.api.data.SimpleLootrInstance;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootr.common.data.LootrInventory;
import noobanidus.mods.lootrmon.common.impl.type.GildedChestType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;

public class GildedLootChestBlockEntity extends RandomizableContainerBlockEntity implements ILootrBlockEntity {
  private final SimpleLootrInstance lootrInstance = new SimpleLootrInstance(this::getVisualOpeners, 27);
  public final GildedState posableState = new GildedState();
  private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
    @Override
    protected void onOpen(Level level, BlockPos pos, BlockState state) {
      if (!GildedLootChestBlockEntity.this.hasBeenOpened()) {
        GildedLootChestBlockEntity.this.lootrInstance.setHasBeenOpened();
        GildedLootChestBlockEntity.this.markChanged();
      }
      playSound(level, pos, state, CobblemonSounds.GILDED_CHEST_OPEN);
    }

    @Override
    protected void onClose(Level level, BlockPos pos, BlockState state) {
      playSound(level, pos, state, CobblemonSounds.GILDED_CHEST_CLOSE);
    }

    @Override
    protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155364_, int p_155365_) {
      GildedLootChestBlockEntity.this.signalOpenCount(level, pos, state, p_155364_, p_155365_);
    }

    @Override
    protected boolean isOwnContainer(Player player) {
      if ((player.containerMenu instanceof ChestMenu menu)) {
        if (menu.getContainer() instanceof LootrInventory data) {
          return GildedLootChestBlockEntity.this.getInfoUUID().equals(data.getInfo().getInfoUUID());
        }
      }

      return false;
    }
  };

  // TODO: Fill this in
  public static Supplier<BlockEntityType<GildedLootChestBlockEntity>> type;

  public GildedLootChestBlockEntity(BlockPos blockPos, BlockState blockState) {
    super(type.get(), blockPos, blockState);
  }

  @Override
  protected Component getDefaultName() {
    return Component.translatable("block.cobblemon.gilded_chest");
  }

  @Override
  protected NonNullList<ItemStack> getItems() {
    return lootrInstance.getEmptyItemList();
  }

  @Override
  protected void setItems(NonNullList<ItemStack> nonNullList) {
  }

  @Override
  public void setInfoReferenceInventory(NonNullList<ItemStack> reference) {
    lootrInstance.setCustomInventory(reference);
  }

  @Override
  protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
    return null;
  }

  @Override
  public int getContainerSize() {
    return 27;
  }

  @Override
  public @Nullable Set<UUID> getClientOpeners() {
    return lootrInstance.getClientOpeners();
  }

  @Override
  public boolean isClientOpened() {
    return lootrInstance.isClientOpened();
  }

  @Override
  public void setClientOpened(boolean b) {
    lootrInstance.setClientOpened(b);
  }

  @Override
  public void markChanged() {
    super.setChanged();
    markDataChanged();
  }

  @Override
  @Deprecated
  public LootrBlockType getInfoBlockType() {
    return LootrBlockType.CHEST;
  }

  @Override
  public ILootrType getInfoNewType() {
    return GildedChestType.type;
  }

  @Override
  public @NotNull UUID getInfoUUID() {
    return lootrInstance.getInfoUUID();
  }

  @Override
  public String getInfoKey() {
    return lootrInstance.getInfoKey();
  }

  @Override
  public boolean hasBeenOpened() {
    return lootrInstance.hasBeenOpened();
  }

  @Override
  public boolean isPhysicallyOpen() {
    if (level != null) {
      if (level.isClientSide()) {
        return posableState.getCurrentPose().equals("open");
      }
    }

    return false;
  }

  @Override
  public void unpackLootTable(@Nullable Player player) {
  }

  @Override
  public int getPhysicalOpenerCount() {
    return this.openersCounter.getOpenerCount();
  }

  public static int getOpenCount(BlockGetter level, BlockPos pos) {
    BlockEntity be = level.getBlockEntity(pos);
    if (be instanceof GildedLootChestBlockEntity chest) {
      return chest.openersCounter.getOpenerCount();
    }
    return 0;
  }

  @Override
  public @NotNull BlockPos getInfoPos() {
    return getBlockPos();
  }

  @Override
  public @Nullable Component getInfoDisplayName() {
    return getDefaultName();
  }

  @Override
  public @NotNull ResourceKey<Level> getInfoDimension() {
    return level.dimension();
  }

  @Override
  public int getInfoContainerSize() {
    return getContainerSize();
  }

  @Override
  public @Nullable NonNullList<ItemStack> getInfoReferenceInventory() {
    return lootrInstance.getCustomInventory();
  }

  @Override
  public boolean isInfoReferenceInventory() {
    return isInfoReferenceInventoryInternal(lootrInstance.isCustomInventory());
  }

  @Override
  public @Nullable ResourceKey<LootTable> getInfoLootTable() {
    return lootTable;
  }

  @Override
  public long getInfoLootSeed() {
    return lootTableSeed;
  }

  @Override
  public Level getInfoLevel() {
    return level;
  }

  private void signalOpenCount(Level level, BlockPos pos, BlockState state, int p155364, int p155365) {
    level.blockEvent(pos, this.getBlockState().getBlock(), 1, p155365);
    if (LootrAPI.isCustomTrapped() && p155364 == p155365 && isInfoReferenceInventory()) {
      Block block = state.getBlock();
      level.updateNeighborsAt(pos, block);
      level.updateNeighborsAt(pos.below(), block);
    }
  }

  @Override
  protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
    super.saveAdditional(compoundTag, provider);
    trySaveLootTable(compoundTag);
    lootrInstance.saveAdditional(compoundTag, provider, level != null && level.isClientSide());
  }

  @Override
  protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
    super.loadAdditional(compoundTag, provider);
    tryLoadLootTable(compoundTag);
    lootrInstance.loadAdditional(compoundTag, provider);
  }

  @Override
  public void saveToItem(ItemStack itemstack, HolderLookup.Provider provider) {
    this.lootrInstance.setSavingToItem(true);
    super.saveToItem(itemstack, provider);
    this.lootrInstance.setSavingToItem(false);
  }

  @Override
  public void startOpen(Player pPlayer) {
    if (!this.remove && !pPlayer.isSpectator()) {
      this.openersCounter.incrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public void stopOpen(Player pPlayer) {
    if (!this.remove && !pPlayer.isSpectator()) {
      this.openersCounter.decrementOpeners(pPlayer, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  public void recheckOpen() {
    if (!this.remove) {
      this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }
  }

  @Override
  public boolean triggerEvent(int i, int j) {
    if (i == 1) {
      var isNowOpen = j > 0;
      var wasOpen = posableState.getCurrentPose().equals("open");
      var model = posableState.getCurrentModel();
      if (model != null) {
        if (isNowOpen && !wasOpen) {
          model.moveToPose(posableState, model.getPose("open"));
        } else {
          model.moveToPose(posableState, model.getPose("closed"));
        }
      }
      return true;
    }
    return super.triggerEvent(i, j);
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
    CompoundTag result = super.getUpdateTag(provider);
    lootrInstance.fillUpdateTag(result, provider, level != null && level.isClientSide());
    return result;
  }

  @Override
  public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
    return ClientboundBlockEntityDataPacket.create(this, BlockEntity::getUpdateTag);
  }

  protected static void playSound(Level pLevel, BlockPos pPos, BlockState pState, SoundEvent pSound) {
    double d0 = (double) pPos.getX() + 0.5D;
    double d1 = (double) pPos.getY() + 0.5D;
    double d2 = (double) pPos.getZ() + 0.5D;

    pLevel.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, pLevel.random.nextFloat() * 0.1F + 0.9F);
  }

  @Override
  public double getParticleYOffset() {
    return 1.1;
  }

  @AutoService(ILootrBlockEntityConverter.class)
  public static class DefaultBlockEntityConverter implements ILootrBlockEntityConverter<GildedLootChestBlockEntity> {

    @Override
    public ILootrBlockEntity apply(GildedLootChestBlockEntity blockEntity) {
      return blockEntity;
    }

    @Override
    public BlockEntityType<?> getBlockEntityType() {
      return type.get();
    }
  }
}
