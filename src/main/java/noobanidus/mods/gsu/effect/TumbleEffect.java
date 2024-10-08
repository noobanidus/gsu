package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;

public class TumbleEffect extends SimpleEffect  {
  public TumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0x532eca);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }
}
