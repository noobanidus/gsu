package noobanidus.mods.gsu.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import noobanidus.mods.gsu.block.entity.DecayingBlockEntity;
import noobanidus.mods.gsu.init.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class CrawlBlock extends BaseEntityBlock {
  private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

  public CrawlBlock(Properties props) {
    super(props);
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
    return pAdjacentBlockState.is(this) || super.skipRendering(pState, pAdjacentBlockState, pSide);
  }

  @SuppressWarnings("deprecation")
  @Override
  public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
    return SHAPE;
  }


  @Override
  public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
    return new DecayingBlockEntity(ModBlockEntities.DECAYING.get(), pos, state);
  }

  @Nullable
  @Override
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
    if (pLevel.isClientSide()) {
      return null;
    }

    if (pBlockEntityType.getRegistryName().equals(ModBlockEntities.DECAYING.getId())) {
      return DecayingBlockEntity::decayingTick;
    }

    return null;
  }
}
