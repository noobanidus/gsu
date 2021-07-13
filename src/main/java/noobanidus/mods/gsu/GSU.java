package noobanidus.mods.gsu;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import noobanidus.libs.noobutil.registrate.CustomRegistrate;
import noobanidus.mods.gsu.commands.CommandPotionId;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModEffects;
import noobanidus.mods.gsu.init.ModSounds;
import noobanidus.mods.gsu.setup.ClientInit;
import noobanidus.mods.gsu.setup.CommonSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GSU.MODID)
@Mod.EventBusSubscriber(modid = GSU.MODID)
public class GSU {
  public static final String MODID = "gsu";
  public static CustomRegistrate REGISTRATE;
  public static Logger LOG = LogManager.getLogger();

  public GSU() {
    REGISTRATE = CustomRegistrate.create(MODID);

    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_CONFIG);
    ConfigManager.loadConfig(ConfigManager.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve(MODID + "-common.toml"));
    modBus.addListener(CommonSetup::init);
    modBus.addListener(ConfigManager::configReloaded);

    DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClientInit::init);

    ModSounds.load();
    ModEffects.load();
  }

  @SubscribeEvent
  public static void commandRegister(RegisterCommandsEvent event) {
    CommandPotionId.register(event.getDispatcher());
  }
}
