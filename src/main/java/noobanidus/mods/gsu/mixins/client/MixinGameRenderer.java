package noobanidus.mods.gsu.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import noobanidus.mods.gsu.init.ModEffects;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class MixinGameRenderer {
  @WrapOperation(
      method = "renderLevel",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getProjectionMatrix(D)Lorg/joml/Matrix4f;")
  )
  private Matrix4f gsu$flipProjection(GameRenderer instance, double fov, Operation<Matrix4f> original) {
    Matrix4f matrix = original.call(instance, fov);
    if (gsu$isMirrorEnabled()) {
      matrix.scale(-1.0f, 1.0f, 1.0f);
    }
    return matrix;
  }

  @Inject(method = "renderLevel", at = @At("HEAD"))
  private void gsu$flipCullFace(CallbackInfo ci) {
    if (gsu$isMirrorEnabled()) {
      GL11.glFrontFace(GL11.GL_CW);
    }
  }

  @Inject(method = "renderLevel", at = @At("RETURN"))
  private void gsu$restoreCullFace(CallbackInfo ci) {
    if (gsu$isMirrorEnabled()) {
      GL11.glFrontFace(GL11.GL_CCW);
    }
  }

  @Unique
  private static boolean gsu$isMirrorEnabled() {
    Minecraft mc = Minecraft.getInstance();
    if (mc == null || mc.player == null) {
      return false;
    }

    return mc.player.hasEffect(ModEffects.MIRROR);
  }
}
