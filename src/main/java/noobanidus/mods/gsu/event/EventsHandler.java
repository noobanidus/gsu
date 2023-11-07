package noobanidus.mods.gsu.event;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.capability.SkinCapability;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.SimpleEffect;
import noobanidus.mods.gsu.init.ModEffects;
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
      Player player = event.getEntity();
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
  public static void attachCapbilities(AttachCapabilitiesEvent<Entity> event) {
    if (ConfigManager.getEntitySet().contains(event.getObject().getType())) {
      event.addCapability(SkinCapability.IDENTIFIER, new SkinCapability());
    }
  }

  @SubscribeEvent
  public static void startTracking(PlayerEvent.StartTracking event) {
    Entity target = event.getTarget();
    if (!target.level().isClientSide()) {
      target.getCapability(Capabilities.SKIN_CAPABILITY).ifPresent(cap -> {
        if (cap.getOverride() != null) {
          Networking.sendTo(new SetSkin(target.getId(), cap.getOverride()), (ServerPlayer) event.getEntity());
        }
      });
    }
  }

/*  @SubscribeEvent
  public static void onEffectRemoved (MobEffectEvent.Remove event) {
    LivingEntity pLivingEntity = event.getEntity();
    if (event.getEffect().equals(ModEffects.DYING.get()) || event.getEffect().equals(ModEffects.IMMORTAL_DYING.get())) {
      if (ConfigManager.debugEffects()) {
        GSU.LOG.error("MobEffectEvent.Remove (`dying` or `immortal_dying`) called for " + pLivingEntity);
      }
      float oldHealth = pLivingEntity.getHealth();
      event.getEntity().hurt(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
      if (ConfigManager.debugEffects()) {
        if (pLivingEntity.getHealth() >= oldHealth && !pLivingEntity.isDeadOrDying()) {
          GSU.LOG.error("Health of entity " + pLivingEntity + " didn't change!");
        } else if (pLivingEntity.isAlive()) {
          GSU.LOG.error("Entity " + pLivingEntity + " is still alive after dying effect ended!");
        }
      }
    }
  }*/

  @SubscribeEvent
  public static void throwableHit(ProjectileImpactEvent event) {
    if (!event.getProjectile().level().isClientSide() && event.getProjectile() instanceof ThrownPotion thrown) {
      ItemStack itemstack = thrown.getItem();
      List<MobEffectInstance> effects = PotionUtils.getMobEffects(itemstack);
      HitResult.Type type = event.getRayTraceResult().getType();
      if (type == HitResult.Type.MISS) {
        return;
      }
      boolean doFire = false;
      for (MobEffectInstance effect : effects) {
        if (effect.getEffect() == ModEffects.DELAYED_FIRE.get() || effect.getEffect() == ModEffects.INSTANT_FIRE.get()) {
          doFire = true;
          break;
        }
      }
      if (doFire) {
        BlockPos starting;
        if (type == HitResult.Type.ENTITY) {
          starting = ((EntityHitResult) event.getRayTraceResult()).getEntity().blockPosition().above();
        } else if (type == HitResult.Type.BLOCK) {
          starting = ((BlockHitResult) event.getRayTraceResult()).getBlockPos();
        } else {
          return;
        }
        Level level = thrown.level();
        for (BlockPos pos : getPositionsWithinCircle(starting, ConfigManager.getFireRadius())) {
          for (int count = -4; count <= 4; count++) {
            BlockPos newPos = pos.offset(0, count, 0);
            BlockState stateAt = level.getBlockState(newPos);
            BlockState stateBelow = level.getBlockState(newPos.below());
            if (stateAt.isAir() && !stateBelow.isAir()) {
              level.setBlock(newPos, Blocks.FIRE.defaultBlockState(), 3);
              break;
            }
          }
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
