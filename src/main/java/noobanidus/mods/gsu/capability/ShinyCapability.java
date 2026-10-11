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

public class ShinyCapability implements ICapabilityProvider, ICapabilitySerializable<StringTag> {
  public static final ResourceLocation IDENTIFIER = new ResourceLocation(GSU.MODID, "shiny_capability");

  private GlintType glint = null;

  @Nullable
  public GlintType getGlint() {
    return glint;
  }

  public void setGlint(GlintType glint) {
    this.glint = glint;
  }

  @NotNull
  @Override
  public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
    return Capabilities.SHINY_CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this));
  }

  @Override
  public StringTag serializeNBT() {
    return StringTag.valueOf(glint == null ? "" : glint.getSerializedName());
  }

  @Override
  public void deserializeNBT(StringTag nbt) {
    String value = nbt.getAsString();
    if (value.isEmpty()) {
      glint = null;
    } else {
      glint = GlintType.fromString(value);
    }
  }
}
