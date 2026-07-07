package noobanidus.mods.gsu.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.*;

import java.util.Collection;

public class ModEffects {
  private static final DeferredRegister<MobEffect> REGISTER = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, GSU.MODID);

  public static Collection<DeferredHolder<MobEffect, ?>> getEntries() {
    return REGISTER.getEntries();
  }

  public static final DeferredHolder<MobEffect, DyingEffect> DYING = REGISTER.register("dying", DyingEffect::new);
  public static final DeferredHolder<MobEffect, SimpleEffect> IMMORTAL = REGISTER.register("immortal", () -> new SimpleEffect.HiddenParticleEffect(MobEffectCategory.NEUTRAL, 0xffffff));
  public static final DeferredHolder<MobEffect, DyingEffect> IMMORTAL_DYING = REGISTER.register("immortal_dying", DyingEffect::new);
  public static final DeferredHolder<MobEffect, FumbleEffect> FUMBLE = REGISTER.register("fumble", FumbleEffect::new);
  public static final DeferredHolder<MobEffect, InstantExplosiveEffect> EXPLOSIVE = REGISTER.register("explosive", InstantExplosiveEffect::new);
  public static final DeferredHolder<MobEffect, DelayedExplosiveEffect> DELAYED_EXPLOSIVE = REGISTER.register("delayed_explosive", DelayedExplosiveEffect::new);
  public static final DeferredHolder<MobEffect, StumbleEffect> STUMBLE = REGISTER.register("stumble", StumbleEffect::new);
  public static final DeferredHolder<MobEffect, CrumbleEffect> CRUMBLE = REGISTER.register("crumble", CrumbleEffect::new);
  public static final DeferredHolder<MobEffect, DrumbleEffect> DRUMBLE = REGISTER.register("drumble", DrumbleEffect::new);
  public static final DeferredHolder<MobEffect, TumbleEffect> TUMBLE = REGISTER.register("tumble", TumbleEffect::new);
  public static final DeferredHolder<MobEffect, ThimbleEffect> THIMBLE = REGISTER.register("thimble", ThimbleEffect::new);
  public static final DeferredHolder<MobEffect, JumbleEffect> JUMBLE = REGISTER.register("jumble", JumbleEffect::new);
  public static final DeferredHolder<MobEffect, InstantFireEffect> INSTANT_FIRE = REGISTER.register("instant_fire", InstantFireEffect::new);
  public static final DeferredHolder<MobEffect, DelayedFireEffect> DELAYED_FIRE = REGISTER.register("delayed_fire", DelayedFireEffect::new);
  public static final DeferredHolder<MobEffect, AutoRunEffect> AUTO_RUN = REGISTER.register("auto_run", AutoRunEffect::new);
  public static final DeferredHolder<MobEffect, ShaderEffect> CREEPER = REGISTER.register("creeper", () -> new ShaderEffect(894731, () -> EntityType.CREEPER));
  public static final DeferredHolder<MobEffect, ShaderEffect> SPIDER = REGISTER.register("spider", () -> new ShaderEffect(11013646, () -> EntityType.SPIDER));
  public static final DeferredHolder<MobEffect, ShaderEffect> ENDERMAN = REGISTER.register("enderman", () -> new ShaderEffect(1447446, () -> EntityType.ENDERMAN));

  private static final ResourceLocation KNOCKBACK_MODIFIER = ResourceLocation.fromNamespaceAndPath(GSU.MODID, "knockback_modifier");

  public static final DeferredHolder<MobEffect, MobEffect> KNOCKUP = REGISTER.register("knockup", () -> new SimpleEffect.HiddenParticleEffect(MobEffectCategory.BENEFICIAL, 0x000000).addAttributeModifier(Attributes.ATTACK_KNOCKBACK, KNOCKBACK_MODIFIER, ConfigManager::getKnockupAmount, AttributeModifier.Operation.ADD_VALUE));

  public static final DeferredHolder<MobEffect, SimpleEffect> CACTUS_SHIELD = REGISTER.register("cactus_shield", () -> new SimpleEffect.HiddenParticleEffect(MobEffectCategory.BENEFICIAL, 0x237543));

  public static final DeferredHolder<MobEffect, SimpleEffect> ARMOR_SHIELD = REGISTER.register("armor_shield", () -> new SimpleEffect.HiddenParticleEffect(MobEffectCategory.BENEFICIAL, 0xd9d48d));

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }
}
