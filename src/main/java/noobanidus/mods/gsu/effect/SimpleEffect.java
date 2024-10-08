package noobanidus.mods.gsu.effect;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleSupplier;

public class SimpleEffect extends MobEffect {
  public SimpleEffect(MobEffectCategory typeIn, int liquidColorIn) {
    super(typeIn, liquidColorIn);
  }

  // Return true to prevent expiration
  public boolean onEffectExpire (LivingEntity entity, int amplifier) {
    return onEffectRemoved(entity, amplifier);
  }

  // Return true to prevent removal
  public boolean onEffectRemoved (LivingEntity entity, int amplifier) {
    return false;
  }

  public boolean hideParticles() {
    return ConfigManager.getHideParticles();
  }

  private record AttributeSupplier (Holder<Attribute> attribute, ResourceLocation id, DoubleSupplier supplier, AttributeModifier.Operation operation) {}

  private final List<AttributeSupplier> attributeSuppliers = new ArrayList<>();

  public SimpleEffect addAttributeModifier(Holder<Attribute> p_316656_, ResourceLocation p_350368_, DoubleSupplier p_19475_, AttributeModifier.Operation p_19476_) {
    attributeSuppliers.add(new AttributeSupplier(p_316656_, p_350368_, p_19475_, p_19476_));
    return this;
  }

  public void finalizeEffect () {
    for (AttributeSupplier supplier : attributeSuppliers) {
     this.addAttributeModifier(supplier.attribute, supplier.id, supplier.supplier.getAsDouble(), supplier.operation);
    }
    attributeSuppliers.clear();
  }

  public static class HiddenParticleEffect extends SimpleEffect {

    public HiddenParticleEffect(MobEffectCategory typeIn, int liquidColorIn) {
      super(typeIn, liquidColorIn);
    }

    @Override
    public boolean hideParticles() {
      return true;
    }
  }
}
