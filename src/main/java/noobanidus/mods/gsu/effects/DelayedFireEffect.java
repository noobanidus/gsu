package noobanidus.mods.gsu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.potion.EffectType;
import noobanidus.mods.gsu.config.ConfigManager;

public class DelayedFireEffect extends SimpleEffect {
  public DelayedFireEffect() {
    this(ConfigManager.getHideParticles());
  }

  public DelayedFireEffect(boolean hide) {
    super(EffectType.HARMFUL, 0xcf1920, hide);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
    super.removeAttributeModifiers(entity, attributeMapIn, amplifier);
    if (!entity.level.isClientSide) {
      entity.setSecondsOnFire(ConfigManager.getFireDuration());
    }
  }
}
