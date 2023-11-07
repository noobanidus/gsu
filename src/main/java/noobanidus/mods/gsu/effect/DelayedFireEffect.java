package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.SimpleEffect;

public class DelayedFireEffect extends SimpleEffect {
  public DelayedFireEffect() {
    this(ConfigManager.getHideParticles());
  }

  public DelayedFireEffect(boolean hide) {
    super(MobEffectCategory.HARMFUL, 0xcf1920, hide);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMapIn, int amplifier) {
    super.removeAttributeModifiers(entity, attributeMapIn, amplifier);
    if (!entity.level().isClientSide) {
      entity.setSecondsOnFire(ConfigManager.getFireDuration());
    }
  }
}
