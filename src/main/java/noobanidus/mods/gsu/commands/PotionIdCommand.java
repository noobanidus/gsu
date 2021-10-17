package noobanidus.mods.gsu.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.PotionArgument;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.StringTextComponent;
import noobanidus.mods.gsu.config.ConfigManager;

public class PotionIdCommand {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    if (ConfigManager.getRegisterPotion()) {
      dispatcher.register(Commands.literal("potion_id").requires(o -> o.hasPermission(ConfigManager.getPermissionLevel())).then(Commands.argument("effect", PotionArgument.effect()).executes(c -> {
        Effect effect = PotionArgument.getEffect(c, "effect");
        byte potion_id = (byte)Effect.getId(effect);
        c.getSource().sendSuccess(new StringTextComponent("Id of `" + effect.getRegistryName().toString() + "` in this environment is: " + potion_id), true);
        return 1;
      })));
    }
  }
}
