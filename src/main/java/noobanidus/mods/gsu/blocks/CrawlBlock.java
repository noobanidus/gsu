package noobanidus.mods.gsu.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BreakableBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import noobanidus.libs.noobutil.tiles.DecayingTileEntity;
import noobanidus.mods.gsu.init.ModTiles;

import javax.annotation.Nullable;

public class CrawlBlock extends BreakableBlock {
  private static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

  public CrawlBlock(Properties props) {
    super(props);
  }

  @Override
  public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
    return SHAPE;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new DecayingTileEntity(ModTiles.DECAYING.get());
  }
}
