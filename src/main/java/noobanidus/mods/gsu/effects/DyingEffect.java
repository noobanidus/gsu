package noobanidus.mods.gsu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

import java.util.Collections;
import java.util.List;

public class DyingEffect extends SimpleEffect {
  public DyingEffect() {
    super(EffectType.NEUTRAL, 0xffffff, true);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeModifierManager pAttributeMap, int pAmplifier) {
    pLivingEntity.hurt(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
  }

  @Override
  public List<ItemStack> getCurativeItems() {
    return Collections.emptyList();
  }
}
