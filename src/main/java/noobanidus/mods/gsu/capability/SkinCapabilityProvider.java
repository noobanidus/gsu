package noobanidus.mods.gsu.capability;

import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import noobanidus.mods.gsu.GSU;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;

public class SkinCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<StringNBT> {
  public static final ResourceLocation IDENTIFIER = new ResourceLocation(GSU.MODID, "skin_capability");

  @CapabilityInject(SkinCapability.class)
  public static final Capability<SkinCapability> SKIN_CAPABILITY = injected();

  private final SkinCapability instance = SKIN_CAPABILITY.getDefaultInstance();

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    return cap == SKIN_CAPABILITY ? LazyOptional.of(() ->  (T) this.instance) : LazyOptional.empty();
  }

  @Override
  public StringNBT serializeNBT() {
    return (StringNBT) SKIN_CAPABILITY.getStorage().writeNBT(SKIN_CAPABILITY, this.instance, null);
  }

  @Override
  public void deserializeNBT(StringNBT nbt) {
    SKIN_CAPABILITY.getStorage().readNBT(SKIN_CAPABILITY, this.instance, null, nbt);
  }

  private static final Object NULL = null;

  @SuppressWarnings("unchecked")
  private static <T> T injected() {
    return (T) NULL;
  }


}
