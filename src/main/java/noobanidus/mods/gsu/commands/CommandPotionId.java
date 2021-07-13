package noobanidus.mods.gsu.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.PotionArgument;
import net.minecraft.potion.Effect;
import net.minecraft.util.text.StringTextComponent;

public class CommandPotionId {
  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    dispatcher.register(Commands.literal("potion_id").requires(o -> o.hasPermissionLevel(2)).then(Commands.argument("effect", PotionArgument.mobEffect()).executes(c -> {
      Effect effect = PotionArgument.getMobEffect(c, "effect");
      int potion_id = Effect.getId(effect);
      c.getSource().sendFeedback(new StringTextComponent("Id of `" + effect.getRegistryName().toString() + "` in this environment is: " + potion_id), true);
      return 1;
    })));
  }
}
