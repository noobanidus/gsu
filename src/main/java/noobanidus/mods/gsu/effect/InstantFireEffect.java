package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.SimpleEffect;

public class InstantFireEffect extends SimpleEffect {
  public InstantFireEffect() {
    super(MobEffectCategory.HARMFUL, 0xcf1920);
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (!entity.level().isClientSide()) {
      entity.setSecondsOnFire(ConfigManager.getFireDuration());
    }
  }
}
