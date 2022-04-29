package noobanidus.mods.gsu.mixins;

import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.capability.SkinCapabilityProvider;
import noobanidus.mods.gsu.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingRenderer.class)
public class MixinLivingRenderer<T extends LivingEntity, M extends EntityModel<T>> {
  @ModifyVariable(at = @At("STORE"), ordinal = 0, method = "getRenderType(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;")
  private ResourceLocation gsuGetRenderType(ResourceLocation def, T entity) {
    if (ConfigManager.getEntitySet().contains(entity.getType())) {
      LazyOptional<SkinCapability> capPossibility = entity.getCapability(SkinCapabilityProvider.SKIN_CAPABILITY);
      if (capPossibility.isPresent()) {
        ResourceLocation result = capPossibility.orElseThrow(IllegalStateException::new).getOverride();
        if (result != null) {
          return result;
        }
      }
    }
    return def;
  }
}
