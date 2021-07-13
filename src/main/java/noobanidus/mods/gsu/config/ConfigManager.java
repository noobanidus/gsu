package noobanidus.mods.gsu.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import noobanidus.mods.gsu.GSU;

import java.nio.file.Path;
import java.util.Random;

public class ConfigManager {
  private static Random rand = new Random();
  private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

  public static ForgeConfigSpec COMMON_CONFIG;

  private static ForgeConfigSpec.DoubleValue EXPLOSION_DAMAGE;
  private static ForgeConfigSpec.DoubleValue EXPLOSION_SIZE;
  private static ForgeConfigSpec.IntValue DAMAGE_AMOUNT;
  private static ForgeConfigSpec.DoubleValue DAMAGE_CHANCE;
  private static ForgeConfigSpec.BooleanValue NICE_MODE;
  private static ForgeConfigSpec.IntValue FUMBLE_CHANCE;
  private static ForgeConfigSpec.IntValue DRUMBLE_CHANCE;
  private static ForgeConfigSpec.BooleanValue EFFECTS_PERSIST;

  public static boolean getEffectsPersist() {
    return EFFECTS_PERSIST.get();
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
    COMMON_BUILDER.push("effects");
    FUMBLE_CHANCE = COMMON_BUILDER.comment("the chance of dropping an item per tick, expressed as 1 in X").defineInRange("fumble_chance", 24, 0, Integer.MAX_VALUE);
    DRUMBLE_CHANCE = COMMON_BUILDER.comment("the chance of being granted slowness x while under the effects of the drumble debuff, expressed as 1 in X").defineInRange("fumble_chance", 30, 0, Integer.MAX_VALUE);
    DAMAGE_AMOUNT = COMMON_BUILDER.comment("the maximum amount of durability damage applied (randomly from 1 to X)").defineInRange("durability_damage", 3, 0, Integer.MAX_VALUE);
    DAMAGE_CHANCE = COMMON_BUILDER.comment("the chance as a percent per tick of the potion effect that a tool or sword will take durability damage").defineInRange("durability_chance", 0.015, 0, Double.MAX_VALUE);
    NICE_MODE = COMMON_BUILDER.comment("whether or not crumble will damage items at or below 10 durability").define("nice_mode", true);
    EFFECTS_PERSIST = COMMON_BUILDER.comment("whether or not potion effects given by bees should persist through death").define("effects_persist", true);
    COMMON_BUILDER.pop();
    COMMON_BUILDER.push("commands");
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
