package noobanidus.mods.gsu.mixins;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import noobanidus.mods.gsu.effects.SimpleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EffectInstance.class)
public class MixinEffectInstance {
  @Unique
  private boolean checkedParticles = false;

  @Shadow
  private boolean showParticles;

  @Inject(method = "doesShowParticles", at = @At(value="HEAD"))
  protected void injectShowParticles(CallbackInfoReturnable<Boolean> cir) {
    if (!checkedParticles) {
      Effect effect = ((EffectInstance) (Object) this).getPotion();
      if (effect instanceof SimpleEffect) {
        if (((SimpleEffect) effect).hideParticles()) {
          this.showParticles = false;
        }
      }
      checkedParticles = true;
    }
  }
}
