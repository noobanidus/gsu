package noobanidus.mods.gsu.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.capability.SkinCapabilityProvider;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effects.SimpleEffect;
import noobanidus.mods.gsu.network.Networking;
import noobanidus.mods.gsu.network.SetSkin;

import java.util.*;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class EventsHandler {
  private static Map<UUID, List<EffectInstance>> potionClone = new HashMap<>();

  @SubscribeEvent
  public static void playerClone(PlayerEvent.Clone event) {
    if (ConfigManager.getEffectsPersist()) {
      PlayerEntity original = event.getOriginal();
      Collection<EffectInstance> instance = original.getActiveEffects();
      if (!instance.isEmpty()) {
        List<EffectInstance> map = potionClone.computeIfAbsent(original.getUUID(), (k) -> new ArrayList<>());
        for (EffectInstance effect : original.getActiveEffects()) {
          if (effect.getEffect() instanceof SimpleEffect) {
            EffectInstance copy = new EffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier());
            map.add(copy);
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
    if (ConfigManager.getEffectsPersist() && !event.isEndConquered()) {
      PlayerEntity player = event.getPlayer();
      List<EffectInstance> effects = potionClone.get(player.getUUID());
      if (effects != null) {
        for (EffectInstance effect : effects) {
          player.addEffect(effect);
        }
        potionClone.remove(player.getUUID());
      }
    }
  }

  @SubscribeEvent
  public static void attachCapbilities (AttachCapabilitiesEvent<Entity> event) {
    if (ConfigManager.getEntitySet().contains(event.getObject().getType())) {
      event.addCapability(SkinCapabilityProvider.IDENTIFIER, new SkinCapabilityProvider());
    }
  }

  @SubscribeEvent
  public static void startTracking (PlayerEvent.StartTracking event) {
    Entity target = event.getTarget();
    if (!target.level.isClientSide()) {
    target.getCapability(SkinCapabilityProvider.SKIN_CAPABILITY).ifPresent(cap -> {
      if (cap.getOverride() != null) {
        Networking.sendTo(new SetSkin(target.getId(), cap.getOverride()), (ServerPlayerEntity) event.getPlayer());
      }
    });
    }
  }

  @SubscribeEvent
  public static void entityJoinWorld (EntityJoinWorldEvent event) {
    if (ConfigManager.getGoalEntitySet().contains(event.getEntity().getType()) && event.getEntity() instanceof MobEntity mob) {
      Set<Goal> toRemove = new HashSet<>();
      mob.targetSelector.availableGoals.forEach(goal -> {
        if (ConfigManager.getGoalStripSet().contains(goal.getClass())) {
          toRemove.add(goal);
        }
      });
      toRemove.forEach(mob.targetSelector::removeGoal);
      toRemove.clear();
      mob.goalSelector.availableGoals.forEach(goal -> {
        if (ConfigManager.getGoalStripSet().contains(goal.getClass())) {
          toRemove.add(goal);
        }
      });
      toRemove.forEach(mob.goalSelector::removeGoal);
    }
  }
}
