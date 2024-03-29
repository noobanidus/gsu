package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effects.*;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModEffects {
  public static final RegistryEntry<DyingEffect> DYING = REGISTRATE.effect("dying", DyingEffect::new).register();
  public static final RegistryEntry<SimpleEffect> IMMORTAL = REGISTRATE.effect("immortal", () -> new SimpleEffect(EffectType.NEUTRAL, 0xffffff, true)).register();
  public static final RegistryEntry<DyingEffect> IMMORTAL_DYING = REGISTRATE.effect("immortal_dying", DyingEffect::new).register();
  public static final RegistryEntry<FumbleEffect> FUMBLE = REGISTRATE.effect("fumble", FumbleEffect::new).register();
  public static final RegistryEntry<InstantExplosiveEffect> EXPLOSIVE = REGISTRATE.effect("explosive", InstantExplosiveEffect::new).register();
  public static final RegistryEntry<DelayedExplosiveEffect> DELAYED_EXPLOSIVE = REGISTRATE.effect("delayed_explosive", DelayedExplosiveEffect::new).register();
  public static final RegistryEntry<StumbleEffect> STUMBLE = REGISTRATE.effect("stumble", StumbleEffect::new).register();

  public static final RegistryEntry<CrumbleEffect> CRUMBLE = REGISTRATE.effect("crumble", CrumbleEffect::new).register();

  public static final RegistryEntry<DrumbleEffect> DRUMBLE = REGISTRATE.effect("drumble", DrumbleEffect::new).register();

  public static final RegistryEntry<TumbleEffect> TUMBLE = REGISTRATE.effect("tumble", TumbleEffect::new).register();

  public static final RegistryEntry<ThimbleEffect> THIMBLE = REGISTRATE.effect("thimble", ThimbleEffect::new).register();

  public static final RegistryEntry<JumbleEffect> JUMBLE = REGISTRATE.effect("jumble", JumbleEffect::new).register();

  public static final RegistryEntry<InstantFireEffect> INSTANT_FIRE = REGISTRATE.effect("instant_fire", InstantFireEffect::new).register();
  public static final RegistryEntry<DelayedFireEffect> DELAYED_FIRE = REGISTRATE.effect("delayed_fire", DelayedFireEffect::new).register();

  private static final String KNOCKBACK_MODIFIER = "135f711e-33b6-457f-8c40-a5abc8c47a5e";

  public static final RegistryEntry<Effect> KNOCKBACK = REGISTRATE.effect("knockback", () -> new SimpleEffect(EffectType.BENEFICIAL, 0x000000, true).addAttributeModifier(Attributes.ATTACK_KNOCKBACK, KNOCKBACK_MODIFIER, ConfigManager.getKnockbackAmount(), AttributeModifier.Operation.ADDITION)).register();

  public static final RegistryEntry<Effect> KNOCKUP = REGISTRATE.effect("knockup", () -> new SimpleEffect(EffectType.BENEFICIAL, 0x000000, true).addAttributeModifier(Attributes.ATTACK_KNOCKBACK, KNOCKBACK_MODIFIER, ConfigManager.getKnockupAmount(), AttributeModifier.Operation.ADDITION)).register();

  public static final RegistryEntry<SimpleEffect> CACTUS_SHIELD = REGISTRATE.effect("cactus_shield", () -> new SimpleEffect(EffectType.BENEFICIAL, 0x237543, true)).register();

  public static void load() {
  }
}
