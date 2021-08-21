package noobanidus.mods.gsu.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
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
  public static void onDamage(LivingAttackEvent event) {
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

  @SubscribeEvent
  public static void onKnockup (LivingKnockBackEvent event) {
    LivingEntity living = event.getEntityLiving();
    if (living.getRevengeTarget() instanceof MobEntity) {
      MobEntity attacker = (MobEntity) living.getRevengeTarget();
      if (living.equals(attacker.getAttackTarget()) && attacker.getActivePotionEffect(ModEffects.KNOCKUP.get()) != null) {
        event.setCanceled(true);
        float strength = event.getStrength();
        double ratioX = event.getRatioX();
        strength = (float)((double)strength * (1.0D - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
        if (!(strength <= 0.0F)) {
          living.isAirBorne = true;
          Vector3d vector3d = living.getMotion();
          Vector3d vector3d1 = (new Vector3d(0.0d, ratioX, 0.0d)).normalize().scale(strength);
          living.setMotion(vector3d.x, vector3d1.y, vector3d.z);
        }
      }
    }
  }
}
