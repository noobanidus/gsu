package noobanidus.mods.gsu.capability;

import net.minecraft.util.ResourceLocation;

public class SkinCapability {
  private ResourceLocation override = null;

  public ResourceLocation getOverride() {
    return override;
  }

  public void setOverride(ResourceLocation override) {
    this.override = override;
  }
}
