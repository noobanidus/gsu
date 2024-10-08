package noobanidus.mods.gsu.mixins;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.effect.SimpleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public class MixinMobEffectInstance {
  @Unique
  private boolean gsu_1_21$checkedParticles = false;

  @Shadow
  private boolean visible;

  @Inject(method = "isVisible", at = @At(value="HEAD"))
  protected void GSUinjectIsVisible(CallbackInfoReturnable<Boolean> cir) {
    if (!gsu_1_21$checkedParticles) {
      Holder<MobEffect> effect = ((MobEffectInstance) (Object) this).getEffect();
      if (effect.value() instanceof SimpleEffect simpleEffect) {
        if (simpleEffect.hideParticles()) {
          this.visible = false;
        }
      } else if (effect.is(GSUTags.Potions.SUPPRESS_PARTICLES)) {
        this.visible = false;
      }
      gsu_1_21$checkedParticles = true;
    }
  }
}
