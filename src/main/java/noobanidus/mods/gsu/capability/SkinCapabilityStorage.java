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
    if (instance.getOverride() != null) {
      return StringNBT.valueOf(instance.getOverride().toString());
    } else {
      return StringNBT.valueOf("");
    }
  }

  @Override
  public void readNBT(Capability<SkinCapability> capability, SkinCapability instance, Direction side, INBT nbt) {
    String result = nbt.getAsString();
    if (!result.isEmpty()) {
      instance.setOverride(new ResourceLocation(result));
    }
  }
}
