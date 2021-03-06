package noobanidus.mods.gsu.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeHooks;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.Random;

public class FumbleEffect extends SimpleEffect {
  private static final Random rand = new Random();
  private static int OFF_HAND_SLOT = 40;

  public FumbleEffect() {
    super(EffectType.HARMFUL, 0x10eb26);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof PlayerEntity && !entity.level.isClientSide()) {
      if (rand.nextInt(ConfigManager.getFumbleChance()) != 0) {
        // Don't drop an item every tick
        return;
      }
      PlayerEntity player = (PlayerEntity) entity;
      ItemStack stack = ItemStack.EMPTY;
      int slot = 0;
      for (int i = 0; i < amplifier + 1; i++) {
        switch (rand.nextInt(20)) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
            int tries = 100;
            while (stack.isEmpty()) {
              stack = player.inventory.getItem(slot = rand.nextInt(36));
              tries--;
              if (tries < 0) {
                break;
              }
            }
            if (!stack.isEmpty() && stack.onDroppedByPlayer(player)) {
              if (ForgeHooks.onPlayerTossEvent(player, player.inventory.removeItem(slot, 1), false) != null) {
                break;
              }
            }
          case 9:
          case 10:
          case 11:
          case 12:
            stack = player.inventory.getSelected();
            if (!stack.isEmpty() && stack.onDroppedByPlayer(player)) {
              if (ForgeHooks.onPlayerTossEvent(player, player.inventory.removeItem(player.inventory.selected, 1), false) != null) {
                break;
              }
            }
          case 13:
          case 14:
          case 15:
            stack = player.getOffhandItem();
            if (!stack.isEmpty() && stack.onDroppedByPlayer(player)) {
              if (ForgeHooks.onPlayerTossEvent(player, player.inventory.removeItem(OFF_HAND_SLOT, 1), false) != null) {
                break;
              }
            }
          case 16:
          case 17:
          case 18:
          case 19:
            slot = rand.nextInt(4);
            stack = player.inventory.getItem(slot);
            if (!stack.isEmpty() && stack.onDroppedByPlayer(player)) {
              if (ForgeHooks.onPlayerTossEvent(player, player.inventory.removeItem(35 + slot, stack.getCount()), false) != null) {
                break;
              }
            }
        }
      }
    }
  }
}
