package noobanidus.mods.gsu.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import noobanidus.mods.gsu.config.ConfigManager;

public class DelayedExplosiveEffect extends SimpleEffect {
  public DelayedExplosiveEffect () {
    this(ConfigManager.getHideParticles());
  }

  public DelayedExplosiveEffect(boolean hide) {
    super(MobEffectCategory.HARMFUL, 0xeb4e10, hide);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMapIn, int amplifier) {
    super.removeAttributeModifiers(entity, attributeMapIn, amplifier);
    if (!entity.level().isClientSide) {
      entity.level().explode(entity, entity.damageSources().explosion(entity, entity), null, entity.getX(), entity.getY(), entity.getZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
  }
}
