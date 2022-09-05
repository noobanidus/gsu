package noobanidus.mods.gsu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;
import net.minecraft.util.DamageSource;
import noobanidus.mods.gsu.config.ConfigManager;

public class InstantFireEffect extends InstantEffect {
  public InstantFireEffect() {
    super(EffectType.HARMFUL, 0xcf1920);
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (!entity.level.isClientSide()) {
      entity.setSecondsOnFire(ConfigManager.getFireDuration());
    }
  }
}
