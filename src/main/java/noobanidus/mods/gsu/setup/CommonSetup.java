package noobanidus.mods.gsu.setup;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.command.CumulativeEffectCommand;
import noobanidus.mods.gsu.command.NightCommand;
import noobanidus.mods.gsu.effect.SimpleEffect;
import noobanidus.mods.gsu.init.ModEffects;

@EventBusSubscriber(modid = GSU.MODID)
public class CommonSetup {
  public static void init(FMLCommonSetupEvent event) {
    event.enqueueWork(() -> {
      // THIS IS SO HACK???
      ((SimpleEffect) ModEffects.KNOCKBACK.get()).finalizeEffect();
      ((SimpleEffect) ModEffects.KNOCKUP.get()).finalizeEffect();
    });
  }

  @SubscribeEvent
  public static void registerCommands (RegisterCommandsEvent event) {
    CumulativeEffectCommand.register(event.getDispatcher(), event.getBuildContext());
    NightCommand.register(event.getDispatcher());
  }
}
