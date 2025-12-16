package noobanidus.mods.lootrmon.common.client;

import com.cobblemon.mod.common.client.CobblemonResources;
import com.cobblemon.mod.common.client.render.models.blockbench.PosableModel;
import com.cobblemon.mod.common.client.render.models.blockbench.PosableState;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.RenderContext;
import com.cobblemon.mod.common.client.render.models.blockbench.repository.VaryingModelRepository;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import noobanidus.mods.lootr.common.client.ClientHooks;
import noobanidus.mods.lootrmon.common.LootrmonIds;
import noobanidus.mods.lootrmon.common.block.entity.GildedLootChestBlockEntity;

import java.util.HashSet;
import java.util.Set;

public class GildedLootChestBlockRenderer implements BlockEntityRenderer<GildedLootChestBlockEntity> {
  private final RenderContext renderContext = new RenderContext();
  {
    renderContext.put(RenderContext.Companion.getRENDER_STATE(), RenderContext.RenderState.BLOCK);
    renderContext.put(RenderContext.Companion.getDO_QUIRKS(), true);
  }

  public GildedLootChestBlockRenderer(BlockEntityRendererProvider.Context context) {

  }

  @Override
  public void render(GildedLootChestBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
    Player player = ClientHooks.getPlayer();
    if (player == null) {
      return;
    }

    ResourceLocation poserId = LootrmonIds.crl("gilded_chest");
    PosableState state = blockEntity.posableState;
    Set<String> aspects = new HashSet<>();
    state.setCurrentAspects(aspects);
    state.updatePartialTicks(f);

    ResourceLocation texture = blockEntity.hasClientOpened(player) ? LootrmonIds.rl("textures/block/functional/gilded_loot_chest_open.png") : LootrmonIds.crl("textures/block/functional/gilded_chest.png");

    PosableModel model = VaryingModelRepository.INSTANCE.getPoser(poserId, state);
    model.context = renderContext;
    var consumer = multiBufferSource.getBuffer(RenderType.entityCutout(texture));
    model.setBufferProvider(multiBufferSource);
    state.setCurrentModel(model);
    renderContext.put(RenderContext.Companion.getASPECTS(), aspects);
    renderContext.put(RenderContext.Companion.getTEXTURE(), texture);
    renderContext.put(RenderContext.Companion.getSPECIES(), poserId);
    renderContext.put(RenderContext.Companion.getPOSABLE_STATE(), state);

    poseStack.pushPose();
    poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
    poseStack.translate(-0.5, 0, 0.5);
    poseStack.mulPose(Axis.YP.rotationDegrees(blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()));
    poseStack.mulPose(Axis.YP.rotationDegrees(180f));

    model.applyAnimations(
        null,
        state,
        0f, 0f, 0f, 0f, state.getAnimationSeconds() * 20
    );
    model.render(renderContext, poseStack, consumer, i, j, -0x1);
    var layers = VaryingModelRepository.INSTANCE.getLayers(poserId, state);
    layers.forEach(layer -> {
      model.setLayerContext(multiBufferSource, state, layers);
      model.render(renderContext, poseStack, consumer, i, j, -0x1);
      model.resetLayerContext();
    });
    model.setDefault();
    poseStack.popPose();
  }
}
