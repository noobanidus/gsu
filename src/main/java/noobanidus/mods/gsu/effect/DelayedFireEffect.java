package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.config.ConfigManager;

public class DelayedFireEffect extends SimpleEffect {
  public DelayedFireEffect() {
    super(MobEffectCategory.HARMFUL, 0xcf1920);
  }

  @Override
  public boolean onEffectRemoved(LivingEntity entity, int amplifier) {
    if (!entity.level().isClientSide) {
      entity.setRemainingFireTicks(ConfigManager.getFireDuration());
    }
    return false;
  }
}
