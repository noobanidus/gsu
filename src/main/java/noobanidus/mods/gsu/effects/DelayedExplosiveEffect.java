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
  public void removeAttributesModifiersFromEntity(LivingEntity entity, AttributeModifierManager attributeMapIn, int amplifier) {
    super.removeAttributesModifiersFromEntity(entity, attributeMapIn, amplifier);
    if (!entity.world.isRemote) {
      entity.world.createExplosion(entity, DamageSource.causeExplosionDamage(entity), null, entity.getPosX(), entity.getPosY(), entity.getPosZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
  }
}
