package noobanidus.mods.gsu.effect;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.Random;

public class DrumbleEffect extends SimpleEffect {
  private static final Random rand = new Random();

  public DrumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0x9c0000);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (rand.nextInt(8) == 0) {
      entity.level.addParticle(ParticleTypes.END_ROD, entity.getRandomX(1.0), entity.getRandomY() + 0.5, entity.getRandomZ(1.0), 0, 0, 0);
    }
    if (!entity.level.isClientSide && rand.nextInt(ConfigManager.getDrumbleChance()) == 0) {
      if (entity.getEffect(MobEffects.MOVEMENT_SLOWDOWN) == null) {
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, 10, false, false, true));
      }
    }
  }
}
