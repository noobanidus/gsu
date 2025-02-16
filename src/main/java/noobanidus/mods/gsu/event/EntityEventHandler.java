package noobanidus.mods.gsu.event;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModAttachments;
import noobanidus.mods.gsu.mixins.AccessorMixinAttributeMap;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@EventBusSubscriber
public class EntityEventHandler {
  private static final Set<Class<?>> ATTACK_CLASSES_CACHE = new HashSet<>();
  private static final Set<Class<?>> IGNORE_CLASSES = new HashSet<>();

  private static final Set<Class<?>> KEPT_CLASSES_CACHE = new HashSet<>();

  public static boolean isAttackGoal(Goal g) {
    return g instanceof MeleeAttackGoal || g instanceof RangedAttackGoal || isAttackTask(g);
  }

  public static boolean shouldKeepGoal(Goal g) {
    return g instanceof FloatGoal || g instanceof LookAtPlayerGoal || g instanceof ClimbOnTopOfPowderSnowGoal || g instanceof DoorInteractGoal || g instanceof PathfindToRaidGoal<?> || isKeptTask(g);
  }

  @SubscribeEvent
  public static void onEntityLoaded(EntityJoinLevelEvent event) {
    if (!(event.getEntity() instanceof PathfinderMob mob) || !mob.getData(ModAttachments.PLAYER_HOSTILE)) {
      return;
    }

    AttributeMap attributeMap = mob.getAttributes();

    if (!attributeMap.hasAttribute(Attributes.ATTACK_DAMAGE)) {
      AttributeInstance attribute = new AttributeInstance(Attributes.ATTACK_DAMAGE, a -> {
      });
      attribute.setBaseValue(1);
      ((AccessorMixinAttributeMap) attributeMap).getAttributes().put(Attributes.ATTACK_DAMAGE, attribute);
    }

    Set<WrappedGoal> attackTasks = new HashSet<>();
    for (WrappedGoal task : mob.goalSelector.getAvailableGoals()) {
      Goal goal1 = task.getGoal();
      if (isAttackGoal(goal1)) {
        attackTasks.add(task);
      }
    }

    Set<WrappedGoal> essentialTasks = new HashSet<>();
    for (WrappedGoal task : mob.goalSelector.getAvailableGoals()) {
      Goal goal1 = task.getGoal();
      if (shouldKeepGoal(goal1)) {
        essentialTasks.add(task);
      }
    }

    mob.goalSelector.getAvailableGoals().clear();
    mob.targetSelector.getAvailableGoals().clear();
    if (!attackTasks.isEmpty()) {
      for (WrappedGoal goal : attackTasks) {
        mob.goalSelector.addGoal(goal.getPriority(), goal.getGoal());
      }
    } else {
      mob.goalSelector.addGoal(0, new MeleeAttackGoal(mob, 1.2D, true));
    }
    for (WrappedGoal goal : essentialTasks) {
      mob.goalSelector.addGoal(goal.getPriority(), goal.getGoal());
    }
    mob.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(mob, Player.class, 10, true, false, e -> true));
    mob.targetSelector.addGoal(1, new HurtByTargetGoal(mob));
  }

  private static boolean isAttackTask(Goal goal) {
    if (IGNORE_CLASSES.contains(goal.getClass())) {
      return false;
    } else if (ATTACK_CLASSES_CACHE.contains(goal.getClass())) {
      return true;
    } else {
      if (goal.getClass().getSimpleName().toLowerCase(Locale.ROOT).contains("attack")) {
        ATTACK_CLASSES_CACHE.add(goal.getClass());
        return true;
      } else {
        IGNORE_CLASSES.add(goal.getClass());
        return false;
      }
    }
  }

  private static boolean isKeptTask(Goal goal) {
    if (KEPT_CLASSES_CACHE.contains(goal.getClass())) {
      return true;
    }
    if (isAttackGoal(goal)) {
      return false;
    }
    String className = goal.getClass().getSimpleName().toLowerCase(Locale.ROOT);
    for (String s : ConfigManager.getGoalsToKeep()) {
      if (className.contains(s)) {
        KEPT_CLASSES_CACHE.add(goal.getClass());
        return true;
      }
    }
    return false;
  }
}
