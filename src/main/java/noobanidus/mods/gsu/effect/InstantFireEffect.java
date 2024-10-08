package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.config.ConfigManager;

public class InstantFireEffect extends SimpleEffect {
  public InstantFireEffect() {
    super(MobEffectCategory.HARMFUL, 0xcf1920);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    if (!entity.level().isClientSide()) {
      entity.igniteForTicks(ConfigManager.getFireDuration());
    }
    return false;
  }
}
