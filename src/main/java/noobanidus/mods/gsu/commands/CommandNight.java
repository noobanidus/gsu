package noobanidus.mods.gsu.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;

public class CommandNight {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    dispatcher.register(Commands.literal("midnight").requires(o -> o.hasPermissionLevel(2)).executes(c -> {
      MinecraftServer server = c.getSource().getServer();
      ServerWorld world = server.getWorld(World.OVERWORLD);
      if (world != null) {
        ServerWorldInfo info = (ServerWorldInfo) world.getWorldInfo();
        long dayTime = info.getDayTime();
        long newTime = (dayTime + 24000L);
        newTime -= newTime % 24000;
        world.setDayTime(newTime + 17800);
        return 1;
      }
      return 0;
    }));
  }
}
