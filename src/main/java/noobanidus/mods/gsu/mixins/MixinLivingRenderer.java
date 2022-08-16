package noobanidus.mods.gsu.mixins;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingRenderer<T extends LivingEntity, M extends EntityModel<T>> {
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
}
