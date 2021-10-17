package noobanidus.mods.gsu.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;
import noobanidus.mods.gsu.config.ConfigManager;

public class NightCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    if (ConfigManager.getRegisterTime()) {
      registerCommand(dispatcher, "midnight", ConfigManager.getMidnightTime());
      registerCommand(dispatcher, "night", ConfigManager.getNightTime());
      registerCommand(dispatcher, "dawn", ConfigManager.getDawnTime());
      registerCommand(dispatcher, "midday", ConfigManager.getMiddayTime());
      registerCommand(dispatcher, "morning", ConfigManager.getMorningTime());
      registerCommand(dispatcher, "sunset", ConfigManager.getSunsetTime());
    }
  }

  private static void registerCommand (CommandDispatcher<CommandSource> dispatcher, String command, int value) {
    dispatcher.register(Commands.literal(command).requires(o -> o.hasPermission(ConfigManager.getPermissionLevel())).executes(c -> {
      MinecraftServer server = c.getSource().getServer();
      ServerWorld world = server.getLevel(World.OVERWORLD);
      if (world != null) {
        ServerWorldInfo info = (ServerWorldInfo) world.getLevelData();
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
