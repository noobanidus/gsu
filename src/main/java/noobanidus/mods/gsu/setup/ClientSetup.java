package noobanidus.mods.gsu.setup;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import noobanidus.mods.gsu.GSU;

@EventBusSubscriber(modid = GSU.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientSetup {
  @SubscribeEvent
  public static void init(FMLClientSetupEvent event) {
  }
}
