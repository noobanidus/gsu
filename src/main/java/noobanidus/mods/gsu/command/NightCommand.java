package noobanidus.mods.gsu.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.PrimaryLevelData;
import noobanidus.mods.gsu.config.ConfigManager;

public class NightCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    if (ConfigManager.getRegisterTime()) {
      registerCommand(dispatcher, "midnight", ConfigManager.getMidnightTime());
      registerCommand(dispatcher, "night", ConfigManager.getNightTime());
      registerCommand(dispatcher, "dawn", ConfigManager.getDawnTime());
      registerCommand(dispatcher, "midday", ConfigManager.getMiddayTime());
      registerCommand(dispatcher, "morning", ConfigManager.getMorningTime());
      registerCommand(dispatcher, "sunset", ConfigManager.getSunsetTime());
    }
  }

  private static void registerCommand (CommandDispatcher<CommandSourceStack> dispatcher, String command, int value) {
    dispatcher.register(Commands.literal(command).requires(o -> o.hasPermission(ConfigManager.getPermissionLevel())).executes(c -> {
      MinecraftServer server = c.getSource().getServer();
      ServerLevel world = server.getLevel(Level.OVERWORLD);
      if (world != null) {
        PrimaryLevelData info = (PrimaryLevelData) world.getLevelData();
        long dayTime = info.getDayTime();
        long newTime = (dayTime + ConfigManager.getDayLength());
        newTime -= newTime % ConfigManager.getDayLength();
        world.setDayTime(newTime + value);
        return 1;
      }
      return 0;
    }));
  }
}
