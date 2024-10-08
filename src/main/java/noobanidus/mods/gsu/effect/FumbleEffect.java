package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.CommonHooks;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.config.ConfigManager;

public class FumbleEffect extends SimpleEffect {
  private static final int OFF_HAND_SLOT = 40;

  public FumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0x10eb26);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player player && !entity.level().isClientSide()) {
      if (entity.getRandom().nextInt(ConfigManager.getFumbleChance()) != 0) {
        // Don't drop an item every tick
        return true;
      }
      ItemStack stack = ItemStack.EMPTY;
      int slot = 0;
      for (int i = 0; i < amplifier + 1; i++) {
        switch (entity.getRandom().nextInt(20)) {
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
            while (stack.isEmpty() && !stack.is(GSUTags.Item.FUMBLE_BLACKLIST)) {
              stack = player.getInventory().getItem(slot = entity.getRandom().nextInt(36));
              tries--;
              if (tries < 0) {
                break;
              }
            }
            if (!stack.isEmpty() && stack.onDroppedByPlayer(player)) {
              if (CommonHooks.onPlayerTossEvent(player, player.getInventory().removeItem(slot, 1), false) != null) {
                break;
              }
            }
          case 9:
          case 10:
          case 11:
          case 12:
            stack = player.getInventory().getSelected();
            if (!stack.isEmpty() && !stack.is(GSUTags.Item.FUMBLE_BLACKLIST) && stack.onDroppedByPlayer(player)) {
              if (CommonHooks.onPlayerTossEvent(player, player.getInventory().removeItem(player.getInventory().selected, 1), false) != null) {
                break;
              }
            }
          case 13:
          case 14:
          case 15:
            stack = player.getOffhandItem();
            if (!stack.isEmpty() && !stack.is(GSUTags.Item.FUMBLE_BLACKLIST) && stack.onDroppedByPlayer(player)) {
              if (CommonHooks.onPlayerTossEvent(player, player.getInventory().removeItem(OFF_HAND_SLOT, 1), false) != null) {
                break;
              }
            }
          case 16:
          case 17:
          case 18:
          case 19:
            slot = entity.getRandom().nextInt(4);
            stack = player.getInventory().getItem(slot);
            if (!stack.isEmpty() && !stack.is(GSUTags.Item.FUMBLE_BLACKLIST) && stack.onDroppedByPlayer(player)) {
              if (CommonHooks.onPlayerTossEvent(player, player.getInventory().removeItem(35 + slot, stack.getCount()), false) != null) {
                break;
              }
            }
        }
      }
    }
    return true;
  }
}
