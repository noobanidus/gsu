package noobanidus.mods.gsu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import noobanidus.mods.gsu.config.ConfigManager;

public class DelayedExplosiveEffect extends SimpleEffect {
  public DelayedExplosiveEffect () {
    this(ConfigManager.getHideParticles());
  }

  public DelayedExplosiveEffect(boolean hide) {
    super(EffectType.HARMFUL, 0xeb4e10, hide);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
    super.removeAttributeModifiers(entity, attributeMapIn, amplifier);
    if (!entity.level.isClientSide) {
      entity.level.explode(entity, DamageSource.explosion(entity), null, entity.getX(), entity.getY(), entity.getZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
  }
}
