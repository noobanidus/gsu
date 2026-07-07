package noobanidus.mods.gsu.effect;

import com.google.common.base.Suppliers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import noobanidus.mods.gsu.attachment.EntityEffect;
import noobanidus.mods.gsu.init.ModAttachments;

import java.util.function.Supplier;

public class ShaderEffect extends SimpleEffect {
  private final Supplier<EntityType<?>> type;
  private EntityEffect effectType = null;

  public ShaderEffect(int liquidColorIn, Supplier<EntityType<?>> type) {
    super(MobEffectCategory.HARMFUL, liquidColorIn);
    this.type = Suppliers.memoize(type::get);
  }

  private EntityEffect getType() {
    if (effectType == null) {
      effectType = new EntityEffect(BuiltInRegistries.ENTITY_TYPE.getResourceKey(type.get())
          .orElseThrow(() -> new NullPointerException("Entity " + type + " has not been registered!")));
    }
    return effectType;
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
    if (livingEntity instanceof Player player) {
      var effect = player.getData(ModAttachments.ENTITY_EFFECT);
      if (!effect.is(type.get())) {
        player.setData(ModAttachments.ENTITY_EFFECT, getType());
      }
    }
    return true;
  }

  @Override
  public boolean onEffectRemoved(LivingEntity entity, int amplifier) {
    if (entity.hasData(ModAttachments.ENTITY_EFFECT)) {
      entity.setData(ModAttachments.ENTITY_EFFECT, EntityEffect.NONE);
    }

    return false;
  }
}
