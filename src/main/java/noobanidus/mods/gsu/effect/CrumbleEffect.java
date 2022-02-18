package noobanidus.mods.gsu.effect;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.items.CapabilityItemHandler;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModSounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrumbleEffect extends SimpleEffect {
  private static final Random rand = new Random();

  public CrumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0xa0814a);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player) {
      if (rand.nextDouble() <= ConfigManager.getDamageChance()) {
        List<ItemStack> tools = new ArrayList<>();
        entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(cap -> {
          for (int i = 0; i < cap.getSlots(); i++) {
            ItemStack inSlot = cap.getStackInSlot(i);
            // TODO: Improve this
            if (inSlot.getItem() instanceof TieredItem && inSlot.isDamageableItem()) {
              if (ConfigManager.getNiceMode() && inSlot.getDamageValue() >= inSlot.getMaxDamage() + 10) {
                continue;
              }
              tools.add(inSlot);
            }
          }
        });
        if (entity.getOffhandItem().getItem() instanceof ShieldItem) {
          ItemStack inSlot = entity.getOffhandItem();
          if (!ConfigManager.getNiceMode() || inSlot.getDamageValue() < inSlot.getMaxDamage() + 10) {
            tools.add(inSlot);
          }
        }
        if (!tools.isEmpty()) {
          ItemStack tool = tools.get(rand.nextInt(tools.size()));
          tool.hurtAndBreak(rand.nextInt(Math.max(1, ConfigManager.getDamageAmount())) + 1, entity, (playerEntity) -> {
          });
          entity.level.playSound(null, entity.blockPosition(), ModSounds.CRUMBLE.get(), SoundSource.PLAYERS, 1f, 2f);
        }
      }
    }
  }
}
