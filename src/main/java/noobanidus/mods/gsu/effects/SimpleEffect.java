package noobanidus.mods.gsu.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import noobanidus.mods.gsu.config.ConfigManager;

public class SimpleEffect extends Effect {
  private final boolean particles;

  public SimpleEffect(EffectType typeIn, int liquidColorIn) {
    this(typeIn, liquidColorIn, ConfigManager.getHideParticles());
  }

  public SimpleEffect(EffectType typeIn, int liquidColorIn, boolean particles) {
    super(typeIn, liquidColorIn);
    this.particles = particles;
  }

  public boolean hideParticles() {
    return particles;
  }
}
