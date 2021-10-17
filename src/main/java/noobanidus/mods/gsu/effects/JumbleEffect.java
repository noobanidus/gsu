package noobanidus.mods.gsu.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class JumbleEffect extends SimpleEffect  {
  public JumbleEffect() {
    super(EffectType.HARMFUL, 0xff00de);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }
}
