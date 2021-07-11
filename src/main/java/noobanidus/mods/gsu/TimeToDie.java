package noobanidus.mods.timetodie;

import net.minecraft.item.NameTagItem;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.libs.noobutil.registrate.CustomRegistrate;
import noobanidus.mods.timetodie.commands.CommandPotionId;
import noobanidus.mods.timetodie.init.ModEffects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TimeToDie.MODID)
@Mod.EventBusSubscriber(modid=TimeToDie.MODID)
public class TimeToDie {
  public static final String MODID = "timetodie";
  public static CustomRegistrate REGISTRATE;
  public static Logger LOG = LogManager.getLogger();

  public TimeToDie() {
    REGISTRATE = CustomRegistrate.create(MODID);

    ModEffects.load();
  }

  @SubscribeEvent
  public static void commandRegister (RegisterCommandsEvent event) {
    CommandPotionId.register(event.getDispatcher());
  }
}
