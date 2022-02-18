package noobanidus.mods.gsu.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noobanidus.mods.gsu.config.ConfigManager;

public class InstantExplosiveEffect extends InstantenousMobEffect {
  public InstantExplosiveEffect() {
    super(MobEffectCategory.HARMFUL, 0xeb4e10);
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player && !entity.level.isClientSide) {
      Player player = (Player) entity;
      player.level.explode(player, DamageSource.explosion(player), null, player.getX(), player.getY(), player.getZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
  }
}
