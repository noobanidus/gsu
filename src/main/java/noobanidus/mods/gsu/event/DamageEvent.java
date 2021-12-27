package noobanidus.mods.gsu.event;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModEffects;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class DamageEvent {
  public static Set<UUID> knockupSet = new HashSet<>();

  private static void livingEvent(LivingEvent event, DamageSource source) {
    LivingEntity entity = event.getEntityLiving();
    if (source.getEntity() instanceof LivingEntity) {
      LivingEntity sourceEntity = (LivingEntity) source.getEntity();
      if (sourceEntity.hasEffect(ModEffects.KNOCKUP.get())) {
        knockupSet.add(entity.getUUID());
      }
    }
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

  @SubscribeEvent(priority= EventPriority.HIGHEST)
  public static void onKnockup(LivingKnockBackEvent event) {
    LivingEntity living = event.getEntityLiving();
    if (living.getLastHurtByMob() instanceof MobEntity) {
      MobEntity attacker = (MobEntity) living.getLastHurtByMob();
      EffectInstance instance = attacker.getEffect(ModEffects.KNOCKUP.get());
      if (instance != null) {
        event.setCanceled(true);
        float strength = event.getStrength();
        strength = (float) ((double) strength * (1.0D - living.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE)));
        if (!(strength <= 0.0F)) {
          living.hasImpulse = true;
          Vector3d vector3d = living.getDeltaMovement();
          living.setDeltaMovement(vector3d.x, strength, vector3d.z);
        }
      }
    }
  }
}
