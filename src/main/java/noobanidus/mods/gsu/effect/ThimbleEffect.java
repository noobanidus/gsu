package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ThimbleEffect extends SimpleEffect  {
  public ThimbleEffect() {
    super(MobEffectCategory.HARMFUL, 0xcacbc1);
  }

  @Override
  public boolean shouldApplyEffectTickThisTick(int p_295329_, int p_295167_) {
    return true;
  }

  @Override
  public boolean applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player player) {
      if (!player.level().isClientSide() && player.getRandom().nextInt(24) == 0) {
        BlockState state = player.level().getBlockState(player.blockPosition());
        VoxelShape shape = state.getShape(player.level(), player.blockPosition());
        if (!shape.isEmpty() && shape.bounds().getYsize() < 1) {
          return false;
        }
        player.level().setBlockAndUpdate(player.blockPosition().above(), Blocks.COBWEB.defaultBlockState());
      }
    }
    return false;
  }
}
