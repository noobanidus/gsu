package noobanidus.mods.gsu.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class ThimbleEffect extends SimpleEffect  {
  private static final Random rand = new Random();

  public ThimbleEffect() {
    super(MobEffectCategory.HARMFUL, 0xcacbc1);
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }

  @Override
  public void applyEffectTick(LivingEntity entity, int amplifier) {
    if (entity instanceof Player) {
      if (!entity.level().isClientSide() && rand.nextInt(24) == 0) {
        BlockState state = entity.level().getBlockState(entity.blockPosition());
        VoxelShape shape = state.getShape(entity.level(), entity.blockPosition());
        if (!shape.isEmpty() && shape.bounds().getYsize() < 1) {
          return;
        }
        entity.level().setBlockAndUpdate(entity.blockPosition().above(), Blocks.COBWEB.defaultBlockState());
      }
    }
  }
}
