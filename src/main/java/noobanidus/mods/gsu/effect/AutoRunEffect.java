package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class AutoRunEffect extends SimpleEffect {
  public AutoRunEffect(MobEffectCategory type) {
    super(type, 0xd13317);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
    if (livingEntity instanceof Player player) {
      var move = player.getLookAngle();
      Entity vehicle = player.getRootVehicle();
      double scale = (amplifier + 1) / 10.0;
      var newMove = vehicle.getDeltaMovement().add(move.x * scale, 0, move.z * scale);
      vehicle.setDeltaMovement(newMove);
      if (vehicle != player) {
        vehicle.setYRot(player.getYRot());
        vehicle.setYHeadRot(player.getYHeadRot());
      }
    }
    return true;
  }
}
