package noobanidus.mods.gsu;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.*;
import noobanidus.mods.gsu.network.PacketHandler;
import noobanidus.mods.gsu.setup.CommonSetup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(GSU.MODID)
public class GSU {
  public static final String MODID = "gsu";
  public static Logger LOG = LogManager.getLogger();
  public static final String NETWORK_VERSION = "1.21_1";

  private final PacketHandler PACKET_HANDLER;

  public GSU(ModContainer container, IEventBus modBus) {
    container.registerConfig(ModConfig.Type.COMMON, ConfigManager.COMMON_CONFIG);
    container.registerConfig(ModConfig.Type.CLIENT, ConfigManager.CLIENT_CONFIG);
    modBus.addListener(CommonSetup::init);
    modBus.addListener(ConfigManager::configReloaded);

    PACKET_HANDLER = new PacketHandler(modBus);

    ModBlocks.register(modBus);
    ModBlockEntities.register(modBus);
    ModAttachments.register(modBus);
    ModSounds.register(modBus);
    ModEffects.register(modBus);
  }
}
