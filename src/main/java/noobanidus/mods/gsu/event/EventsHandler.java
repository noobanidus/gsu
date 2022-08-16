package noobanidus.mods.gsu.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.SimpleEffect;
import noobanidus.mods.gsu.network.Networking;
import noobanidus.mods.gsu.network.SetSkin;

import java.util.*;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class EventsHandler {
  private static final Map<UUID, List<MobEffectInstance>> potionClone = new HashMap<>();

  @SubscribeEvent
  public static void playerClone(PlayerEvent.Clone event) {
    if (ConfigManager.getEffectsPersist()) {
      Player original = event.getOriginal();
      Collection<MobEffectInstance> instance = original.getActiveEffects();
      if (!instance.isEmpty()) {
        List<MobEffectInstance> map = potionClone.computeIfAbsent(original.getUUID(), (k) -> new ArrayList<>());
        for (MobEffectInstance effect : original.getActiveEffects()) {
          if (effect.getEffect() instanceof SimpleEffect) {
            MobEffectInstance copy = new MobEffectInstance(effect.getEffect(), effect.getDuration(), effect.getAmplifier());
            map.add(copy);
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void playerRespawn(PlayerEvent.PlayerRespawnEvent event) {
    if (ConfigManager.getEffectsPersist() && !event.isEndConquered()) {
      Player player = event.getPlayer();
      List<MobEffectInstance> effects = potionClone.get(player.getUUID());
      if (effects != null) {
        for (MobEffectInstance effect : effects) {
          player.addEffect(effect);
        }
        potionClone.remove(player.getUUID());
      }
    }
  }

  @SubscribeEvent
  public static void attachCapbilities (AttachCapabilitiesEvent<Entity> event) {
    if (ConfigManager.getEntitySet().contains(event.getObject().getType())) {
      event.addCapability(SkinCapability.IDENTIFIER, new SkinCapability());
    }
  }

  @SubscribeEvent
  public static void startTracking (PlayerEvent.StartTracking event) {
    Entity target = event.getTarget();
    if (!target.level.isClientSide()) {
    target.getCapability(Capabilities.SKIN_CAPABILITY).ifPresent(cap -> {
      if (cap.getOverride() != null) {
        Networking.sendTo(new SetSkin(target.getId(), cap.getOverride()), (ServerPlayer) event.getPlayer());
      }
    });
    }
  }
}
