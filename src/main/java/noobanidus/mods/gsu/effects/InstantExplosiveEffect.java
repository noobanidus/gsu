package noobanidus.mods.gsu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.InstantEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import noobanidus.mods.gsu.config.ConfigManager;

public class InstantExplosiveEffect extends InstantEffect {
  public InstantExplosiveEffect() {
    super(EffectType.HARMFUL, 0xeb4e10);
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof PlayerEntity && !entity.level.isClientSide) {
      PlayerEntity player = (PlayerEntity) entity;
      player.level.explode(player, DamageSource.explosion(player), null, player.getX(), player.getY(), player.getZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
  }
}
