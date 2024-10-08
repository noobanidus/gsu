package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import noobanidus.mods.gsu.init.ModBlocks;

import java.util.Random;

public class StumbleEffect extends SimpleEffect  {
  public StumbleEffect() {
    super(MobEffectCategory.HARMFUL, 0x5ae8ff);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player) {
      if (!entity.level().isClientSide() && entity.getRandom().nextInt(16) == 0) {
        BlockState state = entity.level().getBlockState(entity.blockPosition());
        VoxelShape shape = state.getShape(entity.level(), entity.blockPosition());
        if (!shape.isEmpty() && shape.bounds().getYsize() < 1) {
          return false;
        }
        entity.level().setBlockAndUpdate(entity.blockPosition().above(), ModBlocks.CRAWL.get().defaultBlockState());
      }
    }
    return false;
  }
}
