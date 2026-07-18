package noobanidus.mods.gsu.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MixinMouseHandler {
  @WrapOperation(
      method = "turnPlayer",
      at = @At(
          value = "INVOKE",
          target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"
      )
  )
  private void gsu$invertYawOnly(LocalPlayer instance, double yaw, double pitch, Operation<Void> original) {
    if ((instance.hasEffect(ModEffects.MIRROR) && ConfigManager.shouldFlipControls()) || instance.hasEffect(ModEffects.MIRROR_CONTROLS)) {
      yaw = -yaw;
    }
    original.call(instance, yaw, pitch);
  }
}
