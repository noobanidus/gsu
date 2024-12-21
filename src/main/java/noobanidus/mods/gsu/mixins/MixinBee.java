package noobanidus.mods.gsu.mixins;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import noobanidus.mods.gsu.init.ModAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Bee.class)
public class MixinBee {
  @Inject(method="doHurtTarget", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/animal/Bee;setHasStung(Z)V"), cancellable = true)
  public void GSUStopSting (Entity entity, CallbackInfoReturnable<Boolean> cir) {
    Bee thisBee = ((Bee) (Object) this);
    if (thisBee.getData(ModAttachments.PLAYER_HOSTILE)) {
      cir.setReturnValue(false);
    }
  }

  @Inject(method="doHurtTarget", at=@At(value="INVOKE", target="Lnet/minecraft/world/entity/animal/Bee;stopBeingAngry()V"), cancellable = true)
  public void GSUStopAngry (Entity entity, CallbackInfoReturnable<Boolean> cir) {
    Bee thisBee = ((Bee) (Object) this);
    if (thisBee.getData(ModAttachments.PLAYER_HOSTILE)) {
      cir.setReturnValue(false);
    }
  }

}
