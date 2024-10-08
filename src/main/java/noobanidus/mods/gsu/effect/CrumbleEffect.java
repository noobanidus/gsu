package noobanidus.mods.gsu.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.TieredItem;
import net.neoforged.neoforge.capabilities.Capabilities;
import noobanidus.mods.gsu.config.ConfigManager;
import noobanidus.mods.gsu.init.ModSounds;

import java.util.ArrayList;
import java.util.List;

public class CrumbleEffect extends SimpleEffect {
  public CrumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0xa0814a);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player && !entity.level().isClientSide()) {
      if (entity.getRandom().nextDouble() <= ConfigManager.getDamageChance()) {
        List<ItemStack> tools = new ArrayList<>();
        var cap = entity.getCapability(Capabilities.ItemHandler.ENTITY, null);
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
        if (entity.getOffhandItem().getItem() instanceof ShieldItem) {
          ItemStack inSlot = entity.getOffhandItem();
          if (!ConfigManager.getNiceMode() || inSlot.getDamageValue() < inSlot.getMaxDamage() + 10) {
            tools.add(inSlot);
          }
        }
        if (!tools.isEmpty()) {
          ItemStack tool = tools.get(entity.getRandom().nextInt(tools.size()));
          tool.hurtAndBreak(entity.getRandom().nextInt(Math.max(1, ConfigManager.getDamageAmount())) + 1, (ServerLevel) entity.level(), entity, (item) -> {
          });
          entity.level().playSound(null, entity.blockPosition(), ModSounds.CRUMBLE.get(), SoundSource.PLAYERS, 1f, 2f);
        }
      }
    }
    return true;
  }
}
