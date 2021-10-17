package noobanidus.mods.gsu.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModEffects;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class DamageEvent {
  private static void livingEvent(LivingEvent event, DamageSource source) {
    LivingEntity entity = event.getEntityLiving();
    if (source != DamageSource.OUT_OF_WORLD) {
      if (entity.getEffect(ModEffects.IMMORTAL.get()) != null || entity.getEffect(ModEffects.IMMORTAL_DYING.get()) != null) {
        event.setCanceled(true);
        return;
      }
      if (source == DamageSource.CACTUS && entity.getEffect(ModEffects.CACTUS_SHIELD.get()) != null) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public static void onAttack(LivingAttackEvent event) {
    livingEvent(event, event.getSource());
  }

  @SubscribeEvent
  public static void onHurt(LivingHurtEvent event) {
    livingEvent(event, event.getSource());
  }

  @SubscribeEvent
  public static void onDamage(LivingDamageEvent event) {
    livingEvent(event, event.getSource());
  }

  @SubscribeEvent
  public static void onKnockup(LivingKnockBackEvent event) {
    LivingEntity living = event.getEntityLiving();
    if (living.getLastHurtByMob() instanceof MobEntity) {
      MobEntity attacker = (MobEntity) living.getLastHurtByMob();
      if (living.equals(attacker.getTarget()) && attacker.getEffect(ModEffects.KNOCKUP.get()) != null) {
        event.setCanceled(true);
        float strength = event.getStrength();
        double ratioX = event.getRatioX();
        strength = (float) ((double) strength * (1.0D - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
        if (!(strength <= 0.0F)) {
          living.hasImpulse = true;
          Vector3d vector3d = living.getDeltaMovement();
          Vector3d vector3d1 = (new Vector3d(0.0d, ratioX, 0.0d)).normalize().scale(strength);
          living.setDeltaMovement(vector3d.x, vector3d1.y, vector3d.z);
        }
      }
    }
  }
}
