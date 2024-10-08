package noobanidus.mods.gsu.mixins;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.init.ModAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingRenderer<T extends LivingEntity, M extends EntityModel<T>> {
  @ModifyVariable(at = @At("STORE"), ordinal = 0, method = "getRenderType")
  private ResourceLocation gsuGetRenderType(ResourceLocation def, T entity) {
    if (entity.getType().is(GSUTags.Entity.RESKIN) && entity.hasData(ModAttachments.SKIN)) {
      return entity.getData(ModAttachments.SKIN);
    }
    return def;
  }
}
