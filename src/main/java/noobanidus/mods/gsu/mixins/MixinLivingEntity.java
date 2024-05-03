package noobanidus.mods.gsu.mixins;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.init.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class MixinLivingEntity {
  @Inject(method="getDamageAfterArmorAbsorb", at=@At("HEAD"), cancellable=true)
  protected void GSUArmorAfterAbsorb (DamageSource p_21162_, float p_21163_, CallbackInfoReturnable<Float> cir) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (entity.hasEffect(ModEffects.ARMOR_SHIELD.get())) {
      cir.setReturnValue(p_21163_);
      cir.cancel();
    }
  }

  @Inject( method="getDamageAfterMagicAbsorb", at=@At(value="HEAD"), cancellable=true)
  protected void GSUDamageAfterMagic (DamageSource p_21193_, float p_21194_, CallbackInfoReturnable<Float> cir) {
    LivingEntity entity = (LivingEntity) (Object) this;
    if (entity.hasEffect(ModEffects.ARMOR_SHIELD.get())) {
      cir.setReturnValue(p_21194_);
      cir.cancel();
    }
  }
}
