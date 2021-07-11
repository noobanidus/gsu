package noobanidus.mods.timetodie.events;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.timetodie.TimeToDie;
import noobanidus.mods.timetodie.init.ModEffects;

@Mod.EventBusSubscriber(modid = TimeToDie.MODID)
public class DamageEvent {
  @SubscribeEvent
  public static void onDamage(LivingHurtEvent event) {
    if (event.getEntityLiving().getActivePotionEffect(ModEffects.DYING.get()) != null) {
      if (event.getSource() != DamageSource.OUT_OF_WORLD) {
        event.setCanceled(true);
      }
    }
  }
}
