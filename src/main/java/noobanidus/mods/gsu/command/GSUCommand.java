package noobanidus.mods.gsu.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import noobanidus.mods.gsu.event.EntityEventHandler;

import java.util.List;
import java.util.stream.Collectors;

public class GSUCommand {
  public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context) {
    dispatcher.register(builder(Commands.literal("gsu").requires(p -> p.hasPermission(2))));
  }

  private static List<String> entityIds = null;

  private static RequiredArgumentBuilder<CommandSourceStack, ResourceLocation> suggestEntities () {
    if (entityIds == null) {
      entityIds = BuiltInRegistries.ENTITY_TYPE.keySet().stream().map(ResourceLocation::toString).collect(Collectors.toList());
    }

    return Commands.argument("entity", ResourceLocationArgument.id())
        .suggests((c, build) -> SharedSuggestionProvider.suggest(entityIds, build));
  }

  private static LiteralArgumentBuilder<CommandSourceStack> builder(LiteralArgumentBuilder<CommandSourceStack> builder) {
    builder.executes(c -> {
      c.getSource().sendSuccess(() -> Component.literal("/gsu goals <entity>"), false);
      return 1;
    });
    builder.then(Commands.literal("goals").executes(c -> {
      c.getSource().sendSuccess(() -> Component.literal("/gsu goals <entity>"), false);
      return 1;
    }).then(suggestEntities().executes(c -> {
      ResourceLocation entityKey = ResourceLocationArgument.getId(c, "entity");
      EntityType<?> entity = BuiltInRegistries.ENTITY_TYPE.get(entityKey);
      if (entity == null) {
        c.getSource().sendFailure(Component.literal("Cannot find entity " + entityKey));
        return 1;
      }

      Entity e = entity.create(c.getSource().getLevel());
      if (!(e instanceof Mob mob)) {
        c.getSource().sendFailure(Component.literal("Entity " + entityKey + " is not a mob and thus has no goals."));
        return 1;
      }

      for (WrappedGoal goal : mob.goalSelector.getAvailableGoals()) {
        Goal g = goal.getGoal();
        if (EntityEventHandler.isAttackGoal(g)) {
          c.getSource().sendSuccess(() -> Component.literal("Attack goal being retained: " + g.getClass().getName()), false);
        } else if (EntityEventHandler.shouldKeepGoal(g)) {
          c.getSource().sendSuccess(() -> Component.literal("Goal being retained due to configuration: " + g.getClass().getName()), false);
        } else {
          MutableComponent removed = Component.literal("Goal being removed: ");
          Component copyOnClick = ComponentUtils.copyOnClickText(g.getClass().getName());
          removed.append(copyOnClick);
          c.getSource().sendSuccess(() -> removed, false);
        }
      }

      return 1;
    })));
    return builder;
  }
}
