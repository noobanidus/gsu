package noobanidus.mods.gsu.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import noobanidus.mods.gsu.capability.SkinCapabilityProvider;
import noobanidus.mods.gsu.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
  @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundNBT;)V", shift = At.Shift.AFTER, by = 1))
  private void skinRenderer(CompoundNBT nbt, CallbackInfo ci) {
    Entity entity = (Entity) (Object) this;
    if (nbt.contains("gsu_reskin", Constants.NBT.TAG_STRING) && ConfigManager.getEntitySet().contains(entity.getType())) {
      entity.getCapability(SkinCapabilityProvider.SKIN_CAPABILITY).ifPresent(cap -> {
        cap.setOverride(new ResourceLocation(nbt.getString("gsu_reskin")));
      });
    }
  }
}
