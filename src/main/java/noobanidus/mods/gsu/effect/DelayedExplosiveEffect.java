package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.config.ConfigManager;

public class DelayedExplosiveEffect extends SimpleEffect {
  public DelayedExplosiveEffect() {
    super(MobEffectCategory.HARMFUL, 0xeb4e10);
  }

  @Override
  public boolean onEffectRemoved(LivingEntity entity, int amplifier) {
    if (!entity.level().isClientSide) {
      entity.level().explode(entity, entity.damageSources().explosion(entity, entity), null, entity.getX(), entity.getY(), entity.getZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
    return false;
  }
}
