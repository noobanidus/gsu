package noobanidus.mods.gsu.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Explosion;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.gsu.GSU;

import java.nio.file.Path;
import java.util.*;

public class ConfigManager {
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

  public static ForgeConfigSpec COMMON_CONFIG;

  // Potion effects
  private static final ForgeConfigSpec.DoubleValue EXPLOSION_SIZE;
  private static final ForgeConfigSpec.IntValue DAMAGE_AMOUNT;
  private static final ForgeConfigSpec.DoubleValue DAMAGE_CHANCE;
  private static final ForgeConfigSpec.BooleanValue NICE_MODE;
  private static final ForgeConfigSpec.IntValue FUMBLE_CHANCE;
  private static final ForgeConfigSpec.IntValue DRUMBLE_CHANCE;
  private static final ForgeConfigSpec.BooleanValue EFFECTS_PERSIST;
  private static final ForgeConfigSpec.ConfigValue<String> EXPLOSION_MODE;
  private static final ForgeConfigSpec.BooleanValue HIDE_PARTICLES;
  private static final ForgeConfigSpec.DoubleValue KNOCKBACK_AMOUNT;
  private static final ForgeConfigSpec.DoubleValue KNOCKUP_AMOUNT;
  private static final ForgeConfigSpec.IntValue FIRE_DURATION;
  private static final ForgeConfigSpec.IntValue FIRE_RADIUS;

  // Command options
  private static final ForgeConfigSpec.BooleanValue REGISTER_TIME;
  private static final ForgeConfigSpec.BooleanValue REGISTER_POTION;
  private static final ForgeConfigSpec.IntValue PERMISSION_LEVEL;

  // Time values
  private static final ForgeConfigSpec.LongValue DAY_LENGTH;
  private static final ForgeConfigSpec.IntValue MIDNIGHT_TIME;
  private static final ForgeConfigSpec.IntValue NIGHT_TIME;
  private static final ForgeConfigSpec.IntValue MORNING_TIME;
  private static final ForgeConfigSpec.IntValue SUNSET_TIME;
  private static final ForgeConfigSpec.IntValue DAWN_TIME;
  private static final ForgeConfigSpec.IntValue MIDDAY_TIME;

  // Capability injects
  private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_LIST;

  private static Set<EntityType<?>> ENTITY_SET = null;

  public static boolean getEffectsPersist() {
    return EFFECTS_PERSIST.get();
  }

  public static float getExplosionSize() {
    return (float) (double) EXPLOSION_SIZE.get();
  }

  public static Explosion.BlockInteraction getExplosionMode() {
    switch (EXPLOSION_MODE.get().toLowerCase(Locale.ROOT)) {
      default:
      case "none":
        return Explosion.BlockInteraction.NONE;
      case "break":
        return Explosion.BlockInteraction.BREAK;
      case "destroy":
        return Explosion.BlockInteraction.DESTROY;
    }
  }

  public static int getDrumbleChance() {
    return DRUMBLE_CHANCE.get();
  }

  public static int getFumbleChance() {
    return FUMBLE_CHANCE.get();
  }

  public static boolean getNiceMode() {
    return NICE_MODE.get();
  }

  public static int getDamageAmount() {
    return DAMAGE_AMOUNT.get();
  }

  public static double getDamageChance() {
    return DAMAGE_CHANCE.get();
  }

  static {
    Set<String> MODE_TYPES = new HashSet<>(Arrays.asList("none", "break", "destroy"));
    COMMON_BUILDER.push("reskin");
    ENTITY_LIST = COMMON_BUILDER.comment("list of entities (minecraft:cow, etc) that will have the capacity to be reskinned via NBT").defineList("entity_list", Collections.singletonList("minecraft:cow"), o -> (o instanceof String) && ((String) o).contains(":"));
    COMMON_BUILDER.pop();
    COMMON_BUILDER.push("effects");
    EXPLOSION_SIZE = COMMON_BUILDER.comment("the size of the explosion caused by the explosive effect").defineInRange("explosion_size", 2.0, 0, Double.MAX_VALUE);
    EXPLOSION_MODE = COMMON_BUILDER.comment("the type of explosion mode for blocks  options: NONE, BREAK, DESTROY. none does nothing, break breaks blocks, destroy breaks & destroys blocks.").define("explosion_mode", "break", (o) -> o != null && MODE_TYPES.contains(o.toString().toLowerCase(Locale.ROOT)));
    FUMBLE_CHANCE = COMMON_BUILDER.comment("the chance of dropping an item per tick, expressed as 1 in X").defineInRange("fumble_chance", 24, 0, Integer.MAX_VALUE);
    DRUMBLE_CHANCE = COMMON_BUILDER.comment("the chance of being granted slowness x while under the effects of the drumble debuff, expressed as 1 in X").defineInRange("fumble_chance", 30, 0, Integer.MAX_VALUE);
    DAMAGE_AMOUNT = COMMON_BUILDER.comment("the maximum amount of durability damage applied (randomly from 1 to X)").defineInRange("durability_damage", 3, 0, Integer.MAX_VALUE);
    DAMAGE_CHANCE = COMMON_BUILDER.comment("the chance as a percent per tick of the potion effect that a tool or sword will take durability damage").defineInRange("durability_chance", 0.015, 0, Double.MAX_VALUE);
    NICE_MODE = COMMON_BUILDER.comment("whether or not crumble will damage items at or below 10 durability").define("nice_mode", true);
    EFFECTS_PERSIST = COMMON_BUILDER.comment("whether or not potion effects given by bees should persist through death").define("effects_persist", true);
    KNOCKBACK_AMOUNT = COMMON_BUILDER.comment("the amount of knockback that should be added to an entity's attacks").defineInRange("knockback", 5.0, 0.0, 5.0);
    KNOCKUP_AMOUNT = COMMON_BUILDER.comment("the amount of knockup that should be added to an entity's attacks").defineInRange("knockup", 1.8, 0.0, 5.0);
    HIDE_PARTICLES = COMMON_BUILDER.comment("whether or not potion effects should show particles").define("hide_particles", false);
    FIRE_DURATION = COMMON_BUILDER.comment("how long a fire (or delayed fire) potion effect should set an entity on fire (in seconds)").defineInRange("fire_duration", 5, 0, Integer.MAX_VALUE);
    FIRE_RADIUS = COMMON_BUILDER.comment("the radius of blocks that should be set on fire when a splash potion of fire is thrown").defineInRange("fire_radius", 3, 0, Integer.MAX_VALUE);
    COMMON_BUILDER.pop();
    COMMON_BUILDER.push("commands");

    REGISTER_POTION = COMMON_BUILDER.comment("whether or not the potion id command should be registered [default: true]").define("register_potion", true);
    REGISTER_TIME = COMMON_BUILDER.comment("whether commands should be registered for each time (/midnight, /night, /sunrise, etc) [default: true]").define("register_time", true);
    PERMISSION_LEVEL = COMMON_BUILDER.comment("the permission level required for all commands").defineInRange("permission_level", 2, 0, 4);

    COMMON_BUILDER.push("time commands");

    DAY_LENGTH = COMMON_BUILDER.comment("the length of a day in ticks [default: 24,000]").defineInRange("day_length", 24000L, 0, Long.MAX_VALUE);
    NIGHT_TIME = COMMON_BUILDER.comment("the value of 'night', i.e., when you can sleep in a bed [default: 12542]").defineInRange("night_time", 12010, 0, Integer.MAX_VALUE);
    MIDNIGHT_TIME = COMMON_BUILDER.comment("the value of midnight [default: 18000]").defineInRange("midnight_time", 18000, 0, Integer.MAX_VALUE);
    MORNING_TIME = COMMON_BUILDER.comment("the value of 'morning', i.e., when you wake up [default: 0").defineInRange("morning_time", 0, 0, Integer.MAX_VALUE);
    SUNSET_TIME = COMMON_BUILDER.comment("the value of sunset [default: 12000]").defineInRange("sunset_time", 12000, 0, Integer.MAX_VALUE);
    DAWN_TIME = COMMON_BUILDER.comment("the value of sunrise [default: 23000]").defineInRange("dawn_time", 23000, 0, Integer.MAX_VALUE);
    MIDDAY_TIME = COMMON_BUILDER.comment("the value of midday or noon [default: 6000]").defineInRange("midday_time", 6000, 0, Integer.MAX_VALUE);
    COMMON_BUILDER.pop();
    COMMON_BUILDER.pop();
    COMMON_CONFIG = COMMON_BUILDER.build();
  }

  public static boolean getHideParticles() {
    return HIDE_PARTICLES.get();
  }

  public static boolean getRegisterTime() {
    return REGISTER_TIME.get();
  }

  public static boolean getRegisterPotion() {
    return REGISTER_POTION.get();
  }

  public static int getPermissionLevel() {
    return PERMISSION_LEVEL.get();
  }

  public static Long getDayLength() {
    return DAY_LENGTH.get();
  }

  public static int getMidnightTime() {
    return MIDNIGHT_TIME.get();
  }

  public static int getNightTime() {
    return NIGHT_TIME.get();
  }

  public static int getMorningTime() {
    return MORNING_TIME.get();
  }

  public static int getSunsetTime() {
    return SUNSET_TIME.get();
  }

  public static int getDawnTime() {
    return DAWN_TIME.get();
  }

  public static int getMiddayTime() {
    return MIDDAY_TIME.get();
  }

  public static double getKnockbackAmount() {
    return KNOCKBACK_AMOUNT.get();
  }

  public static double getKnockupAmount () {
    return KNOCKUP_AMOUNT.get();
  }

  public static int getFireDuration() {
    return FIRE_DURATION.get();
  }

  public static int getFireRadius () {
    return FIRE_RADIUS.get();
  }

  public static Set<EntityType<?>> getEntitySet () {
    if (ENTITY_SET == null) {
      ENTITY_SET = new HashSet<>();
      for (String value : ENTITY_LIST.get()) {
        ResourceLocation loc = new ResourceLocation(value);
        EntityType<?> type = ForgeRegistries.ENTITY_TYPES.getValue(loc);
        if (type != null) {
          ENTITY_SET.add(type);
        }
      }
    }

    return ENTITY_SET;
  }

  public static void loadConfig(ForgeConfigSpec spec, Path path) {
    CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
    configData.load();
    spec.setConfig(configData);
  }

  public static void configReloaded(ModConfigEvent event) {
    if (event.getConfig().getType() == ModConfig.Type.COMMON) {
      ENTITY_SET = null;
      COMMON_CONFIG.setConfig(event.getConfig().getConfigData());
      GSU.LOG.info("GSU config reloaded");
    }
  }
}
