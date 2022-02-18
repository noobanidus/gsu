package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;

public class GenericEffect extends SimpleEffect  {
  public GenericEffect() {
    super(MobEffectCategory.HARMFUL, 0x160a82);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }
}
