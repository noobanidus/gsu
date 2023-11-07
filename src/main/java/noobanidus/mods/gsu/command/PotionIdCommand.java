package noobanidus.mods.gsu.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import noobanidus.mods.gsu.config.ConfigManager;

public class PotionIdCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
    if (ConfigManager.getRegisterPotion()) {
      dispatcher.register(Commands.literal("potion_id").requires(o -> o.hasPermission(ConfigManager.getPermissionLevel())).then(Commands.argument("effect", ResourceArgument.resource(context, Registries.MOB_EFFECT)).executes(c -> {
        MobEffect effect = ResourceArgument.getMobEffect(c, "effect").value();
        byte potion_id = (byte)MobEffect.getId(effect);
        MutableComponent result = Component.literal("Id of `");
        result.append(effect.getDisplayName());
        result.append("` in this environment is: " + potion_id);
        c.getSource().sendSuccess(() -> result, true);
        return 1;
      })));
    }
  }
}
