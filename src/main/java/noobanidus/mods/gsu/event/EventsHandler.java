package noobanidus.mods.gsu.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.SimpleEffect;

import java.util.*;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class EventsHandler {
  private static Map<UUID, List<MobEffectInstance>> potionClone = new HashMap<>();

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
}
