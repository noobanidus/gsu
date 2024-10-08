package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;

public class JumbleEffect extends SimpleEffect  {
  public JumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0xff00de);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }
}
