package noobanidus.mods.gsu.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import noobanidus.mods.gsu.network.client.ClientNetworkHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = {"net.minecraft.client.renderer.GameRenderer$1"})
public class MixinGameRenderer {
  @WrapOperation(method = "apply(Lnet/minecraft/client/renderer/GameRenderer$ResourceCache;Lnet/minecraft/server/packs/resources/ResourceManager;Lnet/minecraft/util/profiling/ProfilerFiller;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;checkEntityPostEffect(Lnet/minecraft/world/entity/Entity;)V"))
  private void gsuReApplyEffectShader(GameRenderer instance, Entity entity, Operation<Void> original) {
    original.call(instance, entity);
    var currentChain = ((AccessorMixinGameRenderer) instance).gsu$getPostEffect();
    if (currentChain == null) {
      ClientNetworkHooks.tryRefreshShader();
    }
  }
}
