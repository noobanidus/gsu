package noobanidus.mods.gsu.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class Capabilities {
  public static Capability<SkinCapability> SKIN_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
  });
}
