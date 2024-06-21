package noobanidus.mods.gsu.mixins;

import net.minecraft.nbt.CompoundTag;
import noobanidus.mods.gsu.GSU;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CompoundTag.class)
public class MixinCompoundTag {
  private static final String KEY = "toolType";

  @Inject(method="putDouble", at=@At("HEAD"))
  private void GSUPutDouble(String key, double value, CallbackInfo ci) {
    if (key.equals(KEY)) {
      GSU.LOG.error("Tag '" + KEY + "' was set to value '" + value + "'");
      GSU.LOG.error(StringUtils.join(Thread.currentThread().getStackTrace(), "\n"));
    }
  }
}
