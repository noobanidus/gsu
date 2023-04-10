package noobanidus.mods.gsu.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.MobEffectArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import noobanidus.mods.gsu.config.ConfigManager;

public class PotionIdCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
    if (ConfigManager.getRegisterPotion()) {
      dispatcher.register(Commands.literal("potion_id").requires(o -> o.hasPermission(ConfigManager.getPermissionLevel())).then(Commands.argument("effect", MobEffectArgument.effect()).executes(c -> {
        MobEffect effect = MobEffectArgument.getEffect(c, "effect");
        byte potion_id = (byte)MobEffect.getId(effect);
        MutableComponent result = Component.literal("Id of `");
        result.append(effect.getDisplayName());
        result.append("` in this environment is: " + potion_id);
        c.getSource().sendSuccess(result, true);
        return 1;
      })));
    }
  }
}
