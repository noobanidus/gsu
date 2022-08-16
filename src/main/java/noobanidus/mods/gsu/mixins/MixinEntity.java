package noobanidus.mods.gsu.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
  @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER, by = 1))
  private void skinRenderer(CompoundTag nbt, CallbackInfo ci) {
    Entity entity = (Entity) (Object) this;
    if (nbt.contains("gsu_reskin", Tag.TAG_STRING) && ConfigManager.getEntitySet().contains(entity.getType())) {
      entity.getCapability(Capabilities.SKIN_CAPABILITY).ifPresent(cap -> {
        cap.setOverride(new ResourceLocation(nbt.getString("gsu_reskin")));
      });
    }
  }
}
