package noobanidus.mods.gsu.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.effect.SimpleEffect;
import noobanidus.mods.gsu.init.ModAttachments;
import noobanidus.mods.gsu.init.ModEffects;
import noobanidus.mods.gsu.network.PacketSetShiny;
import noobanidus.mods.gsu.network.PacketSetSkin;

import java.util.*;

@EventBusSubscriber(modid = GSU.MODID)
public class EventsHandler {
  private static final Map<UUID, List<MobEffectInstance>> potionClone = new HashMap<>();

  public static boolean shouldPersist(MobEffectInstance effect) {
    if (!ConfigManager.getEffectsPersistTag()) {
      return true;
    }
    return effect.getEffect().is(GSUTags.Potions.EFFECTS_PERSIST);
  }

  @SubscribeEvent
  public static void playerClone(PlayerEvent.Clone event) {
    if (ConfigManager.getEffectsPersist()) {
      Player original = event.getOriginal();
      Collection<MobEffectInstance> instance = original.getActiveEffects();
      if (!instance.isEmpty()) {
        List<MobEffectInstance> map = potionClone.computeIfAbsent(original.getUUID(), (k) -> new ArrayList<>());
        for (MobEffectInstance effect : original.getActiveEffects()) {
          if (shouldPersist(effect)) {
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

  // Data attachments
  @SubscribeEvent
  public static void startTracking(PlayerEvent.StartTracking event) {
    Entity target = event.getTarget();
    if (!target.level().isClientSide()) {
      ResourceLocation skin = target.getData(ModAttachments.SKIN);
      if (skin != ModAttachments.NO_SKIN) {
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new PacketSetSkin(target.getId(), skin));
      }
      if (target.hasData(ModAttachments.SHINY)) {
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), new PacketSetShiny(target.getId(), target.getData(ModAttachments.SHINY)));
      }
    }
  }

  @SubscribeEvent
  public static void onPotionExpire (MobEffectEvent.Expired event) {
    if (event.getEffectInstance().getEffect().value() instanceof SimpleEffect simpleEffect) {
      if (simpleEffect.onEffectExpire(event.getEntity(), event.getEffectInstance().getAmplifier())) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public static void onPotionRemoved (MobEffectEvent.Remove event) {
    if (event.getEffectInstance() == null) {
      return;
    }
    if (event.getEffectInstance().getEffect().value() instanceof SimpleEffect simpleEffect) {
      if (simpleEffect.onEffectRemoved(event.getEntity(), event.getEffectInstance().getAmplifier())) {
        event.setCanceled(true);
      }
    }
  }

  @SubscribeEvent
  public static void throwableHit(ProjectileImpactEvent event) {
    if (!event.getProjectile().level().isClientSide() && event.getProjectile() instanceof ThrownPotion thrown) {
      ItemStack itemstack = thrown.getItem();
      HitResult.Type type = event.getRayTraceResult().getType();
      if (type == HitResult.Type.MISS) {
        return;
      }
      boolean doFire = false;
      PotionContents potioncontents = itemstack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
      for (MobEffectInstance effect : potioncontents.getAllEffects()) {
        if (effect.getEffect().is(GSUTags.Potions.FIRE_EFFECT)) {
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
