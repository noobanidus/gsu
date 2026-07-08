package noobanidus.mods.gsu.mixins.client;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameRenderer.class)
public interface AccessorMixinGameRenderer {
  @Accessor("postEffect")
  PostChain gsu$getPostEffect ();
}
