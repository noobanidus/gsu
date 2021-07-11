package noobanidus.mods.timetodie.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import java.util.Collections;
import java.util.List;

public class DyingEffect extends Effect {
  public DyingEffect() {
    super(EffectType.NEUTRAL, 0xffffff);
  }

  @Override
  public void removeAttributesModifiersFromEntity(LivingEntity p_111187_1_, AttributeModifierManager p_111187_2_, int p_111187_3_) {
    p_111187_1_.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
  }

  @Override
  public List<ItemStack> getCurativeItems() {
    return Collections.emptyList();
  }
}
