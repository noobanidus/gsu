package noobanidus.mods.gsu.effects;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class GenericEffect extends SimpleEffect  {
  public GenericEffect() {
    super(EffectType.HARMFUL, 0x160a82);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }
}
