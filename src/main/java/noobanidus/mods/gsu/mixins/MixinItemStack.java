package noobanidus.mods.gsu.mixins;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.config.ConfigManager;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class MixinItemStack {
  @Inject(method = "getOrCreateTag", at = @At("HEAD"))
  private void getOrCreateTag(CallbackInfoReturnable<CompoundTag> info) {
    if (!ConfigManager.getCheckNbt()) {
      return;
    }
    ItemStack stack = (ItemStack) (Object) this;
    if (!stack.is(GSUTags.Items.NBT_CHECK)) {
      return;
    }
    CompoundTag current = stack.getTag();
    if (current == null) {
      GSU.LOG.error("NBT Tag was created for ItemStack: " + stack);
      GSU.LOG.error(StringUtils.join(Thread.currentThread().getStackTrace(), "\n"));
    }
  }
}
