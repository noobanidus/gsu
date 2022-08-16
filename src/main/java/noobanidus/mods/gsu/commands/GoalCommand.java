package noobanidus.mods.gsu.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntitySummonArgument;
import net.minecraft.command.arguments.PotionArgument;
import net.minecraft.command.arguments.SuggestionProviders;
import net.minecraft.command.impl.SummonCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import noobanidus.mods.gsu.config.ConfigManager;

public class GoalCommand {

  public static void register(CommandDispatcher<CommandSource> dispatcher) {
    if (ConfigManager.getRegisterGoal()) {
      dispatcher.register(Commands.literal("goal").requires(o -> o.hasPermission(ConfigManager.getPermissionLevel())).then(Commands.argument("entity", EntitySummonArgument.id()).suggests(SuggestionProviders.SUMMONABLE_ENTITIES).executes((c) -> {
        ResourceLocation entity = EntitySummonArgument.getSummonableEntity(c, "entity");
        EntityType<?> type = ForgeRegistries.ENTITIES.getValue(entity);
        if (type == null) {
          throw new SimpleCommandExceptionType(new StringTextComponent("Unknown entity type: " + entity)).create();
        }
        Entity result = type.create(c.getSource().getLevel());
        if (result == null) {
          throw new SimpleCommandExceptionType(new StringTextComponent("Unknown entity type: " + entity)).create();
        }
        if (!(result instanceof MobEntity mob)) {
          throw new SimpleCommandExceptionType(new StringTextComponent("Entity type is not a mob: " + entity)).create();
        }
        // TODO: clickable copy-paste
        for (Goal goal : mob.goalSelector.availableGoals) {
          c.getSource().sendSuccess(getComponent(goal.getClass()), true);
        }
        for (Goal goal : mob.targetSelector.availableGoals) {
          c.getSource().sendSuccess(getComponent(goal.getClass()), true);
        }
        result.remove();
        return 1;
      })));
    }
  }

  public static ITextComponent getComponent (Class<?> clazz) {
    String classValue = clazz.getName();
    return TextComponentUtils.wrapInSquareBrackets((new StringTextComponent(classValue)).withStyle((p_211752_2_) -> p_211752_2_.withColor(TextFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, classValue)).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslationTextComponent("chat.copy.click"))).withInsertion(classValue)));
  }
}
