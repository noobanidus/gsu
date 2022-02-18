package noobanidus.mods.gsu.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class DyingEffect extends SimpleEffect {
  public DyingEffect() {
    super(MobEffectCategory.NEUTRAL, 0xffffff, true);
  }

  @Override
  public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
    pLivingEntity.hurt(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
  }

  @Override
  public List<ItemStack> getCurativeItems() {
    return Collections.emptyList();
  }
}
