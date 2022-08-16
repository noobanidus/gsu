package noobanidus.mods.gsu.capability;


import net.minecraft.core.Direction;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.mods.gsu.GSU;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkinCapability implements ICapabilityProvider, ICapabilitySerializable<StringTag> {
  public static final ResourceLocation IDENTIFIER = new ResourceLocation(GSU.MODID, "skin_capability");

  private ResourceLocation override = null;

  @Nullable
  public ResourceLocation getOverride() {
    return override;
  }

  public void setOverride(ResourceLocation override) {
    this.override = override;
  }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    return Capabilities.SKIN_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this));
  }

  @Override
  public StringTag serializeNBT() {
    return StringTag.valueOf(override == null ? "" : override.toString());
  }

  @Override
  public void deserializeNBT(StringTag nbt) {
    String value = nbt.getAsString();
    if (value.isEmpty()) {
      override = null;
    } else {
      override = ResourceLocation.tryParse(value);
    }
  }
}
