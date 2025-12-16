package noobanidus.mods.lootrmon.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.data.ILootrInfoProvider;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;
import noobanidus.mods.lootrmon.common.block.entity.GildedLootChestBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class GildedLootChestBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
  private static final MapCodec<GildedLootChestBlock> CODEC = simpleCodec(GildedLootChestBlock::new);

  private static final VoxelShape SOUTH_OUTLINE = Shapes.or(Shapes.box(0, 0, 0.25, 1, 1, 0.9375));
  private static final VoxelShape NORTH_OUTLINE = Shapes.or(Shapes.box(0, 0, 0.0625, 1.0, 1.0, 0.75));
  private static final VoxelShape WEST_OUTLINE = Shapes.or(Shapes.box(0.0625, 0.0, 0.0, 0.75, 1.0, 1.0));
  private static final VoxelShape EAST_OUTLINE = Shapes.or(Shapes.box(0.25, 0.0, 0.0, 0.9375, 1, 1));

  public static Supplier<GildedLootChestBlock> type;

  public GildedLootChestBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(getStateDefinition().any()
        .setValue(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH)
        .setValue(BlockStateProperties.WATERLOGGED, false));
  }

  @Override
  protected MapCodec<? extends BaseEntityBlock> codec() {
    return CODEC;
  }

  @Override
  protected VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
    return switch (blockState.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
      case Direction.NORTH -> NORTH_OUTLINE;
      case Direction.SOUTH -> SOUTH_OUTLINE;
      case Direction.WEST -> WEST_OUTLINE;
      default -> EAST_OUTLINE;
    };
  }

  @Override
  protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
    if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
      levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
    }
    return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
  }

  @Override
  protected FluidState getFluidState(BlockState blockState) {
    if (blockState.getValue(BlockStateProperties.WATERLOGGED)) {
      return Fluids.WATER.getSource(false);
    }

    return super.getFluidState(blockState);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.WATERLOGGED);
  }

  @Override
  public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult trace) {
    if (level.isClientSide() || player.isSpectator() || !(player instanceof ServerPlayer serverPlayer)) {
      return InteractionResult.CONSUME;
    }
    if (serverPlayer.isShiftKeyDown()) {
      LootrAPI.handleProviderSneak(ILootrInfoProvider.of(pos, level), serverPlayer);
    } else { // Can these be blocked? I think not
      LootrAPI.handleProviderOpen(ILootrInfoProvider.of(pos, level), serverPlayer);
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  public BlockState getStateForPlacement(BlockPlaceContext context) {
    Direction direction = context.getHorizontalDirection().getOpposite();
    FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
    return this.defaultBlockState().setValue(HorizontalDirectionalBlock.FACING, direction).setValue(BlockStateProperties.WATERLOGGED, fluidstate.getType() == Fluids.WATER);
  }

  @Override
  protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
    super.tick(blockState, serverLevel, blockPos, randomSource);
    BlockEntity blockentity = serverLevel.getBlockEntity(blockPos);
    if (blockentity instanceof GildedLootChestBlockEntity chest) {
      chest.recheckOpen();
    }
  }

  @Override
  public boolean hasAnalogOutputSignal(BlockState pState) {
    return true;
  }

  @Override
  public float getDestroyProgress(BlockState pState, Player pPlayer, BlockGetter pLevel, BlockPos pPos) {
    return LootrAPI.getDestroyProgress(pState, pPlayer, pLevel, pPos, super.getDestroyProgress(pState, pPlayer, pLevel, pPos));
  }

  @Override
  public int getAnalogOutputSignal(BlockState pBlockState, Level pLevel, BlockPos pPos) {
    return LootrAPI.getAnalogOutputSignal(pBlockState, pLevel, pPos, 0);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    return ILootrBlockEntity::ticker;
  }

  @Override
  public void playerDestroy(Level level, Player player, BlockPos blockPos, BlockState blockState, @Nullable BlockEntity blockEntity, ItemStack itemStack) {
    super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
    LootrAPI.playerDestroyed(level, player, blockPos, blockEntity);
  }

  @Override
  protected RenderShape getRenderShape(BlockState blockState) {
    return RenderShape.ENTITYBLOCK_ANIMATED;
  }

  @Override
  protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
    return false;
  }

  @Override
  public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
    return new GildedLootChestBlockEntity(blockPos, blockState);
  }
}
