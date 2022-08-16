package noobanidus.mods.gsu.setup;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.network.Networking;

public class CommonSetup {
  public static void init(FMLCommonSetupEvent event) {
    Networking.INSTANCE.registerMessages();
  }
}
