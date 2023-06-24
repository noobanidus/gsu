package noobanidus.mods.gsu.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.Collections;
import java.util.List;

public class DyingEffect extends SimpleEffect {
  public DyingEffect() {
    super(MobEffectCategory.NEUTRAL, 0xffffff, true);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
    if (ConfigManager.debugEffects()) {
      GSU.LOG.error("DyingEffect removeAttributeModifiers (`dying` or `immortal_dying`) called for " + pLivingEntity);
    }
    float oldHealth = pLivingEntity.getHealth();
    pLivingEntity.hurt(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
    if (pLivingEntity.getHealth() >= oldHealth && !pLivingEntity.isDeadOrDying()) {
      GSU.LOG.error("Health of entity " + pLivingEntity + " didn't change!");
    } else if (pLivingEntity.isAlive()) {
      GSU.LOG.error("Entity " + pLivingEntity + " is still alive after dying effect ended!");
    }
  }

  @Override
  public List<ItemStack> getCurativeItems() {
    return Collections.emptyList();
  }
}
