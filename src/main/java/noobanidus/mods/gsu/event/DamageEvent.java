package noobanidus.mods.gsu.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingKnockBackEvent;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModEffects;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@EventBusSubscriber(modid = GSU.MODID)
public class DamageEvent {
  public static Set<UUID> knockupSet = new HashSet<>();

  private static void livingEvent(LivingEvent event, DamageSource source) {
    LivingEntity entity = event.getEntity();
    if (source.getEntity() instanceof LivingEntity sourceEntity) {
      if (sourceEntity.hasEffect(ModEffects.KNOCKUP.getDelegate())) {
        knockupSet.add(entity.getUUID());
      }
    }
    if (!source.is(DamageTypes.FELL_OUT_OF_WORLD)) {
      if (entity.getEffect(ModEffects.IMMORTAL.getDelegate()) != null || entity.getEffect(ModEffects.IMMORTAL_DYING.getDelegate()) != null) {
        if (event instanceof ICancellableEvent e) {
          e.setCanceled(true);
        }
        return;
      }
      if (source.is(DamageTypes.CACTUS) && entity.getEffect(ModEffects.CACTUS_SHIELD.getDelegate()) != null) {
        if (event instanceof ICancellableEvent e) {
          e.setCanceled(true);
        }
      }
    }
  }

  @SubscribeEvent
  public static void onAttack(LivingIncomingDamageEvent event) {
    livingEvent(event, event.getSource());
  }

  @SubscribeEvent
  public static void onHurt(LivingDamageEvent.Pre event) {
    livingEvent(event, event.getSource());
  }

  @SubscribeEvent
  public static void onDamage(LivingDamageEvent.Post event) {
    livingEvent(event, event.getSource());
  }

  @SubscribeEvent(priority= EventPriority.HIGHEST)
  public static void onKnockup(LivingKnockBackEvent event) {
    LivingEntity living = event.getEntity();
    if (living.getLastHurtByMob() instanceof Mob attacker) {
      MobEffectInstance instance = attacker.getEffect(ModEffects.KNOCKUP.getDelegate());
      if (instance != null) {
        event.setCanceled(true);
        float strength = event.getStrength();
        strength = (float) ((double) strength * (1.0D - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
        if (!(strength <= 0.0F)) {
          living.hasImpulse = true;
          Vec3 vector3d = living.getDeltaMovement();
          living.setDeltaMovement(vector3d.x, ConfigManager.getKnockupAmount(), vector3d.z);
        }
      }
    }
  }
}
