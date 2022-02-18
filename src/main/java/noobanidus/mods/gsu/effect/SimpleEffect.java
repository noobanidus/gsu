package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import noobanidus.mods.gsu.config.ConfigManager;

public class SimpleEffect extends MobEffect {
  private final boolean particles;

  public SimpleEffect(MobEffectCategory typeIn, int liquidColorIn) {
    this(typeIn, liquidColorIn, ConfigManager.getHideParticles());
  }

  public SimpleEffect(MobEffectCategory typeIn, int liquidColorIn, boolean particles) {
    super(typeIn, liquidColorIn);
    this.particles = particles;
  }

  public boolean hideParticles() {
    return particles;
  }
}
