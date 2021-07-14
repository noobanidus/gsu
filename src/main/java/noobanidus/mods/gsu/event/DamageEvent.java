package noobanidus.mods.gsu.event;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModEffects;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class DamageEvent {
  @SubscribeEvent
  public static void onDamage(LivingHurtEvent event) {
    Set<Effect> effects = new HashSet<>();
    for (EffectInstance instance : event.getEntityLiving().getActivePotionEffects()) {
      effects.add(instance.getPotion());
    }
    if (effects.contains(ModEffects.IMMORTAL.get()) || effects.contains(ModEffects.IMMORTAL_DYING.get())) {
      if (event.getSource() != DamageSource.OUT_OF_WORLD) {
        event.setCanceled(true);
      }
    }
  }
}
