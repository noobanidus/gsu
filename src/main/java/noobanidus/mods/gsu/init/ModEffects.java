package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.*;
import noobanidus.mods.gsu.effect.DelayedFireEffect;
import noobanidus.mods.gsu.effect.InstantFireEffect;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModEffects {
  public static final RegistryEntry<DyingEffect> DYING = REGISTRATE.simple("dying", Registries.MOB_EFFECT, DyingEffect::new);
  public static final RegistryEntry<SimpleEffect> IMMORTAL = REGISTRATE.simple("immortal", Registries.MOB_EFFECT, () -> new SimpleEffect(MobEffectCategory.NEUTRAL, 0xffffff, true));
  public static final RegistryEntry<DyingEffect> IMMORTAL_DYING = REGISTRATE.simple("immortal_dying", Registries.MOB_EFFECT, DyingEffect::new);
  public static final RegistryEntry<FumbleEffect> FUMBLE = REGISTRATE.simple("fumble", Registries.MOB_EFFECT, FumbleEffect::new);
  public static final RegistryEntry<InstantExplosiveEffect> EXPLOSIVE = REGISTRATE.simple("explosive", Registries.MOB_EFFECT, InstantExplosiveEffect::new);
  public static final RegistryEntry<DelayedExplosiveEffect> DELAYED_EXPLOSIVE = REGISTRATE.simple("delayed_explosive", Registries.MOB_EFFECT, DelayedExplosiveEffect::new);
  public static final RegistryEntry<StumbleEffect> STUMBLE = REGISTRATE.simple("stumble",Registries.MOB_EFFECT, StumbleEffect::new);

  public static final RegistryEntry<CrumbleEffect> CRUMBLE = REGISTRATE.simple("crumble", Registries.MOB_EFFECT,  CrumbleEffect::new);

  public static final RegistryEntry<DrumbleEffect> DRUMBLE = REGISTRATE.simple("drumble", Registries.MOB_EFFECT, DrumbleEffect::new);

  public static final RegistryEntry<TumbleEffect> TUMBLE = REGISTRATE.simple("tumble", Registries.MOB_EFFECT, TumbleEffect::new);

  public static final RegistryEntry<ThimbleEffect> THIMBLE = REGISTRATE.simple("thimble", Registries.MOB_EFFECT, ThimbleEffect::new);

  public static final RegistryEntry<JumbleEffect> JUMBLE = REGISTRATE.simple("jumble", Registries.MOB_EFFECT, JumbleEffect::new);

  public static final RegistryEntry<InstantFireEffect> INSTANT_FIRE = REGISTRATE.simple("instant_fire", Registries.MOB_EFFECT, InstantFireEffect::new);
  public static final RegistryEntry<DelayedFireEffect> DELAYED_FIRE = REGISTRATE.simple("delayed_fire", Registries.MOB_EFFECT, DelayedFireEffect::new);

  private static final String KNOCKBACK_MODIFIER = "135f711e-33b6-457f-8c40-a5abc8c47a5e";

  public static final RegistryEntry<MobEffect> KNOCKBACK = REGISTRATE.simple("knockback", Registries.MOB_EFFECT, () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0x000000, true).addAttributeModifier(Attributes.ATTACK_KNOCKBACK, KNOCKBACK_MODIFIER, ConfigManager.getKnockbackAmount(), AttributeModifier.Operation.ADDITION));

  public static final RegistryEntry<MobEffect> KNOCKUP = REGISTRATE.simple("knockup", Registries.MOB_EFFECT, () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0x000000, true).addAttributeModifier(Attributes.ATTACK_KNOCKBACK, KNOCKBACK_MODIFIER, ConfigManager.getKnockupAmount(), AttributeModifier.Operation.ADDITION));

  public static final RegistryEntry<SimpleEffect> CACTUS_SHIELD = REGISTRATE.simple("cactus_shield", Registries.MOB_EFFECT, () -> new SimpleEffect(MobEffectCategory.BENEFICIAL, 0x237543, true));

  public static void load() {
  }
}
