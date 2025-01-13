package noobanidus.mods.gsu.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.GlintType;
import noobanidus.mods.gsu.init.ModAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {
  @Inject(method = "load", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V", shift = At.Shift.AFTER, by = 1))
  private void skinRenderer(CompoundTag nbt, CallbackInfo ci) {
    Entity entity = (Entity) (Object) this;
    if (nbt.contains("gsu_reskin", Tag.TAG_STRING) && entity.getType().is(GSUTags.Entity.RESKIN)) {
      //noinspection DataFlowIssue
      entity.setData(ModAttachments.SKIN.get(), ResourceLocation.tryParse(nbt.getString("gsu_reskin")));
    }
    if (nbt.contains("gsu_player_hostile", Tag.TAG_BYTE) && nbt.getBoolean("gsu_player_hostile")) {
      entity.setData(ModAttachments.PLAYER_HOSTILE.get(), true);
    }
    if (nbt.contains("gsu_shiny", Tag.TAG_STRING)) {
      entity.setData(ModAttachments.SHINY.get(), GlintType.fromString(nbt.getString("gsu_shiny")));
    }
  }
}
