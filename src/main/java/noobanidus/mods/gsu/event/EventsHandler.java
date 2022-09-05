package noobanidus.mods.gsu.event;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.PotionEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.capability.SkinCapabilityProvider;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effects.SimpleEffect;
import noobanidus.mods.gsu.init.ModEffects;
import noobanidus.mods.gsu.network.Networking;
import noobanidus.mods.gsu.network.SetSkin;

import java.util.*;

@Mod.EventBusSubscriber(modid = GSU.MODID)
public class EventsHandler {
  private static final Map<UUID, List<EffectInstance>> potionClone = new HashMap<>();

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
  public static void attachCapbilities(AttachCapabilitiesEvent<Entity> event) {
    if (ConfigManager.getEntitySet().contains(event.getObject().getType())) {
      event.addCapability(SkinCapabilityProvider.IDENTIFIER, new SkinCapabilityProvider());
    }
  }

  @SubscribeEvent
  public static void startTracking(PlayerEvent.StartTracking event) {
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
  public static void throwableHit(ProjectileImpactEvent.Throwable event) {
    if (!event.getThrowable().level.isClientSide() && event.getThrowable() instanceof PotionEntity) {
      ItemStack itemstack = ((PotionEntity) event.getThrowable()).getItem();
      List<EffectInstance> effects = PotionUtils.getMobEffects(itemstack);
      RayTraceResult.Type type = event.getRayTraceResult().getType();
      if (type == RayTraceResult.Type.MISS) {
        return;
      }
      boolean doFire = false;
      for (EffectInstance effect : effects) {
        if (effect.getEffect() == ModEffects.DELAYED_FIRE.get() || effect.getEffect() == ModEffects.INSTANT_FIRE.get()) {
          doFire = true;
          break;
        }
      }
      if (doFire) {
        BlockPos starting;
        if (type == RayTraceResult.Type.ENTITY) {
          starting = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity().blockPosition().above();
        } else if (type == RayTraceResult.Type.BLOCK) {
          starting = ((BlockRayTraceResult) event.getRayTraceResult()).getBlockPos().above();
        } else {
          return;
        }
        World level = event.getThrowable().level;
        for (BlockPos pos : getPositionsWithinCircle(starting, ConfigManager.getFireRadius())) {
          for (int count = -4; count <= 4; count++) {
            BlockPos newPos = pos.offset(0, count, 0);
            BlockState stateAt = level.getBlockState(newPos);
            BlockState stateBelow = level.getBlockState(newPos.below());
            if (stateAt.isAir() &&  !stateBelow.isAir()) {
              level.setBlock(newPos, Blocks.FIRE.defaultBlockState(), 3);
              break;
            }
          }
         // try settnig fires
        }
      }
    }
  }

  private static List<BlockPos> getPositionsWithinCircle(BlockPos center, int r) {
    List<BlockPos> positions = new ArrayList<>();
    int x = center.getX();
    int z = center.getZ();
    int y = center.getY();
    int r2 = r * r;
    for (int i = z - r; i < z + r; i++) {
      for (int j = x; (j - x) * (j - x) + (i - z) * (i - z) <= r2; j--) {
        positions.add(new BlockPos(j, y, i));
      }
      for (int j = x + 1; (j - x) * (j - x) + (i - z) * (i - z) <= r2; j++) {
        positions.add(new BlockPos(j, y, i));
      }
    }
    return positions;
  }
}
