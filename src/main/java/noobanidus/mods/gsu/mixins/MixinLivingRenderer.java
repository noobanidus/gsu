package noobanidus.mods.gsu.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.SheetedDecalTextureGenerator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.capability.GlintType;
import noobanidus.mods.gsu.capability.ShinyCapability;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.client.CustomRenderType;
import noobanidus.mods.gsu.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingRenderer<T extends LivingEntity, M extends EntityModel<T>> {
  @Shadow
  public abstract M getModel();

  @Shadow
  protected abstract float getWhiteOverlayProgress(T p_115334_, float p_115335_);

  @Shadow
  protected abstract boolean isBodyVisible(T p_115341_);

  @ModifyVariable(at = @At("STORE"), ordinal = 0, method = "getRenderType")
  private ResourceLocation gsuGetRenderType(ResourceLocation def, T entity) {
    if (ConfigManager.getEntitySet().contains(entity.getType())) {
      LazyOptional<SkinCapability> capPossibility = entity.getCapability(Capabilities.SKIN_CAPABILITY);
      if (capPossibility.isPresent()) {
        ResourceLocation result = capPossibility.orElseThrow(IllegalStateException::new).getOverride();
        if (result != null) {
          return result;
        }
      }
    }
    return def;
  }

  @Inject(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", shift = At.Shift.AFTER))
  private void gsuRenderShiny(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, CallbackInfo ci) {
    if (ConfigManager.getEntitySet().contains(entity.getType())) {
      LazyOptional<ShinyCapability> capPossibility = entity.getCapability(Capabilities.SHINY_CAPABILITY);
      if (capPossibility.isPresent()) {
        GlintType type = capPossibility.orElseThrow(IllegalAccessError::new).getGlint();
        if (type != null) {
          Minecraft minecraft = Minecraft.getInstance();
          boolean invisibleFlag = this.isBodyVisible(entity);
          boolean translucentFlag = !invisibleFlag && !entity.isInvisibleTo(minecraft.player);

          poseStack.pushPose();
          PoseStack.Pose pose = poseStack.last();
          // TODO: Render types for translucent/invisible? TODO
          VertexConsumer consumer = new SheetedDecalTextureGenerator(bufferSource.getBuffer(CustomRenderType.getByGlint(type)), pose.pose(), pose.normal(), 0.0078125f);
          this.getModel().renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, this.getWhiteOverlayProgress(entity, partialTicks)), 1.0F, 1.0F, 1.0F, translucentFlag ? 0.15F : 1.0F);
          poseStack.popPose();
        }
      }
    }
  }
}
