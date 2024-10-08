package noobanidus.mods.gsu.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.EffectCure;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class DyingEffect extends SimpleEffect {
  public DyingEffect() {
    super(MobEffectCategory.NEUTRAL, 0xffffff);
  }

  @Override
  public boolean hideParticles() {
    return true;
  }

  @Override
  public boolean onEffectRemoved (LivingEntity pLivingEntity, int pAmplifier) {
    if (ConfigManager.debugEffects()) {
      GSU.LOG.error("DyingEffect onEffectRemoved (`dying` or `immortal_dying`) called for " + pLivingEntity);
    }
    float oldHealth = pLivingEntity.getHealth();
    pLivingEntity.hurt(pLivingEntity.damageSources().fellOutOfWorld(), Float.MAX_VALUE);
    if (pLivingEntity.getHealth() >= oldHealth && !pLivingEntity.isDeadOrDying()) {
      GSU.LOG.error("Health of entity " + pLivingEntity + " didn't change!");
    } else if (pLivingEntity.isAlive()) {
      GSU.LOG.error("Entity " + pLivingEntity + " is still alive after dying effect ended!");
    }
    return false;
  }

  @Override
  public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
    cures.clear();
  }
}
