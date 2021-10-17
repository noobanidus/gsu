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
  public void performEffect(LivingEntity entity, int amplifier) {
    if (entity instanceof PlayerEntity && !entity.world.isRemote) {
      PlayerEntity player = (PlayerEntity) entity;
      player.world.createExplosion(player, DamageSource.causeExplosionDamage(player), null, player.getPosX(), player.getPosY(), player.getPosZ(), (float) (double) ConfigManager.getExplosionSize(), false, ConfigManager.getExplosionMode());
    }
  }
}
