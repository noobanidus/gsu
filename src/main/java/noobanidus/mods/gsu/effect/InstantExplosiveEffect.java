package noobanidus.mods.gsu.effect;

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
  public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player player && !entity.level().isClientSide) {
      player.level().explode(player, entity.damageSources().explosion(player, player), null, player.getX(), player.getY(), player.getZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
    return false;
  }
}
