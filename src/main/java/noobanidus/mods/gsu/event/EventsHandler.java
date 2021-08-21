package noobanidus.mods.gsu.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effects.SimpleEffect;

import java.util.*;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class EventsHandler {
  private static Map<UUID, List<EffectInstance>> potionClone = new HashMap<>();

  @SubscribeEvent
  public static void playerClone(PlayerEvent.Clone event) {
    if (ConfigManager.getEffectsPersist()) {
      PlayerEntity original = event.getOriginal();
      Collection<EffectInstance> instance = original.getActivePotionEffects();
      if (!instance.isEmpty()) {
        List<EffectInstance> map = potionClone.computeIfAbsent(original.getUniqueID(), (k) -> new ArrayList<>());
        for (EffectInstance effect : original.getActivePotionEffects()) {
          if (effect.getPotion() instanceof SimpleEffect) {
            EffectInstance copy = new EffectInstance(effect.getPotion(), effect.getDuration(), effect.getAmplifier());
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
      List<EffectInstance> effects = potionClone.get(player.getUniqueID());
      if (effects != null) {
        for (EffectInstance effect : effects) {
          player.addPotionEffect(effect);
        }
        potionClone.remove(player.getUniqueID());
      }
    }
  }
}
