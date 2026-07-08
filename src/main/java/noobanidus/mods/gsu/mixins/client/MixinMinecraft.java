package noobanidus.mods.gsu.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import noobanidus.mods.gsu.network.client.ClientNetworkHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {
  @WrapOperation(method = "handleKeybinds", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;checkEntityPostEffect(Lnet/minecraft/world/entity/Entity;)V"))
  private void gsu$reApplyEffectShader(GameRenderer instance, Entity entity, Operation<Void> original) {
    original.call(instance, entity);
    var currentChain = ((AccessorMixinGameRenderer) instance).gsu$getPostEffect();
    if (currentChain == null) {
      ClientNetworkHooks.tryRefreshShader();
    }
  }

  @Inject(method="setCameraEntity", at=@At("TAIL"))
  private void gsu$reApplyEffectShaderCameraEntity (Entity viewingEntity, CallbackInfo ci) {
    var currentChain = ((AccessorMixinGameRenderer) ((Minecraft) (Object)this).gameRenderer).gsu$getPostEffect();
    if (currentChain == null) {
      ClientNetworkHooks.tryRefreshShader();
    }
  }
}
