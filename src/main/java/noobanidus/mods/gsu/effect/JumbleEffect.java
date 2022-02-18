package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;

public class JumbleEffect extends SimpleEffect  {
  public JumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0xff00de);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }
}
