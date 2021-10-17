package noobanidus.mods.gsu.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class TumbleEffect extends SimpleEffect  {
  public TumbleEffect() {
    super(EffectType.HARMFUL, 0x532eca);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }
}
