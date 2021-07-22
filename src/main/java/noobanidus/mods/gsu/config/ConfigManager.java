package noobanidus.mods.gsu.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.world.Explosion;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import noobanidus.mods.gsu.GSU;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class ConfigManager {
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

  public static ForgeConfigSpec COMMON_CONFIG;

  // Potion effects
  private static ForgeConfigSpec.DoubleValue EXPLOSION_SIZE;
  private static ForgeConfigSpec.IntValue DAMAGE_AMOUNT;
  private static ForgeConfigSpec.DoubleValue DAMAGE_CHANCE;
  private static ForgeConfigSpec.BooleanValue NICE_MODE;
  private static ForgeConfigSpec.IntValue FUMBLE_CHANCE;
  private static ForgeConfigSpec.IntValue DRUMBLE_CHANCE;
  private static ForgeConfigSpec.BooleanValue EFFECTS_PERSIST;
  private static ForgeConfigSpec.ConfigValue<String> EXPLOSION_MODE;

  // Command options
  private static ForgeConfigSpec.BooleanValue REGISTER_TIME;
  private static ForgeConfigSpec.BooleanValue REGISTER_POTION;
  private static ForgeConfigSpec.IntValue PERMISSION_LEVEL;

  // Time values
  private static ForgeConfigSpec.LongValue DAY_LENGTH;
  private static ForgeConfigSpec.IntValue MIDNIGHT_TIME;
  private static ForgeConfigSpec.IntValue NIGHT_TIME;
  private static ForgeConfigSpec.IntValue MORNING_TIME;
  private static ForgeConfigSpec.IntValue SUNSET_TIME;
  private static ForgeConfigSpec.IntValue DAWN_TIME;
  private static ForgeConfigSpec.IntValue MIDDAY_TIME;

  public static boolean getEffectsPersist() {
    return EFFECTS_PERSIST.get();
  }

  public static float getExplosionSize() {
    return (float) (double) EXPLOSION_SIZE.get();
  }

  public static Explosion.Mode getExplosionMode() {
    switch (EXPLOSION_MODE.get().toLowerCase(Locale.ROOT)) {
      default:
      case "none":
        return Explosion.Mode.NONE;
      case "break":
        return Explosion.Mode.BREAK;
      case "destroy":
        return Explosion.Mode.DESTROY;
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
    Set<String> MODE_TYPES = new HashSet<>();
    MODE_TYPES.addAll(Arrays.asList("none", "break", "destroy"));
    COMMON_BUILDER.push("effects");
    EXPLOSION_SIZE = COMMON_BUILDER.comment("the size of the explosion caused by the explosive effect").defineInRange("explosion_size", 2.0, 0, Double.MAX_VALUE);
    EXPLOSION_MODE = COMMON_BUILDER.comment("the type of explosion mode for blocks  options: NONE, BREAK, DESTROY. none does nothing, break breaks blocks, destroy breaks & destroys blocks.").define("explosion_mode", "break", (o) -> MODE_TYPES.contains(o.toString().toLowerCase(Locale.ROOT)));
    FUMBLE_CHANCE = COMMON_BUILDER.comment("the chance of dropping an item per tick, expressed as 1 in X").defineInRange("fumble_chance", 24, 0, Integer.MAX_VALUE);
    DRUMBLE_CHANCE = COMMON_BUILDER.comment("the chance of being granted slowness x while under the effects of the drumble debuff, expressed as 1 in X").defineInRange("fumble_chance", 30, 0, Integer.MAX_VALUE);
    DAMAGE_AMOUNT = COMMON_BUILDER.comment("the maximum amount of durability damage applied (randomly from 1 to X)").defineInRange("durability_damage", 3, 0, Integer.MAX_VALUE);
    DAMAGE_CHANCE = COMMON_BUILDER.comment("the chance as a percent per tick of the potion effect that a tool or sword will take durability damage").defineInRange("durability_chance", 0.015, 0, Double.MAX_VALUE);
    NICE_MODE = COMMON_BUILDER.comment("whether or not crumble will damage items at or below 10 durability").define("nice_mode", true);
    EFFECTS_PERSIST = COMMON_BUILDER.comment("whether or not potion effects given by bees should persist through death").define("effects_persist", true);
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

  public static void loadConfig(ForgeConfigSpec spec, Path path) {
    CommentedFileConfig configData = CommentedFileConfig.builder(path).sync().autosave().writingMode(WritingMode.REPLACE).build();
    configData.load();
    spec.setConfig(configData);
  }

  public static void configReloaded(ModConfig.Reloading event) {
    if (event.getConfig().getType() == ModConfig.Type.COMMON) {
      COMMON_CONFIG.setConfig(event.getConfig().getConfigData());
      GSU.LOG.info("GSU config reloaded");
    }
  }
}
