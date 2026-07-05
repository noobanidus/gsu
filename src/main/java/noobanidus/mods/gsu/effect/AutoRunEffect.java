package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AutoRunEffect extends SimpleEffect {
  public AutoRunEffect() {
    super(MobEffectCategory.HARMFUL, 0xd13317);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
    if (livingEntity instanceof Player player) {
      var move = player.getLookAngle();
      var newMove = player.getDeltaMovement().add(move.x, 0, move.y);
      player.setDeltaMovement(newMove);
    }
    return true;
  }
}
