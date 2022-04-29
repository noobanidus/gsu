package noobanidus.mods.gsu.capability;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkinCapabilityStorage implements Capability.IStorage<SkinCapability> {
  @Nullable
  @Override
  public INBT writeNBT(Capability<SkinCapability> capability, SkinCapability instance, Direction side) {
    return StringNBT.valueOf(instance.getOverride().toString());
  }

  @Override
  public void readNBT(Capability<SkinCapability> capability, SkinCapability instance, Direction side, INBT nbt) {
    instance.setOverride(new ResourceLocation(nbt.getAsString()));
  }
}
