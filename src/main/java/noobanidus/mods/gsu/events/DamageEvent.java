package noobanidus.mods.gsu.events;

import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModEffects;

@Mod.EventBusSubscriber(modid = GSU.MODID)
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
