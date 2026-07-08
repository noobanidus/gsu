package noobanidus.mods.gsu.mixins.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.client.CustomRenderType;
import noobanidus.mods.gsu.init.ModAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingRenderer<T extends LivingEntity, M extends EntityModel<T>> {
  @Shadow public abstract M getModel();

  @Shadow protected abstract float getWhiteOverlayProgress(T p_115334_, float p_115335_);

  @Shadow protected abstract boolean isBodyVisible(T p_115341_);

  @ModifyVariable(at = @At("STORE"), ordinal = 0, method = "getRenderType")
  private ResourceLocation gsuGetRenderType(ResourceLocation def, T entity) {
    if (entity.getType().is(GSUTags.Entity.RESKIN) && entity.hasData(ModAttachments.SKIN)) {
      return entity.getData(ModAttachments.SKIN);
    }
    return def;
  }

  @Inject(method="render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at=@At(value="INVOKE", target="Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", shift= At.Shift.AFTER))
  private void gsuRenderShiny (T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
    if (entity.hasData(ModAttachments.SHINY)) {
      Minecraft minecraft = Minecraft.getInstance();
      boolean invisibleFlag = this.isBodyVisible(entity);
      boolean translucentFlag = !invisibleFlag && !entity.isInvisibleTo(minecraft.player);
      boolean glowingFlag = minecraft.shouldEntityAppearGlowing(entity);

      poseStack.pushPose();
      PoseStack.Pose pose = poseStack.last();
      // TODO: Render types for translucent/invisible? TODO
      VertexConsumer consumer = new SheetedDecalTextureGenerator(bufferSource.getBuffer(CustomRenderType.getByGlint(entity.getData(ModAttachments.SHINY))), pose, 0.0078125f);
      this.getModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks)), translucentFlag ? 654311423 : -1);
      poseStack.popPose();
    }
  }
}
