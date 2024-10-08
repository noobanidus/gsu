package noobanidus.mods.gsu.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.gsu.init.ModBlockEntities;

public class DecayingBlockEntity extends BlockEntity {
  private int decay;

  public DecayingBlockEntity(BlockPos position, BlockState state) {
    this(position, state, 35);
  }

  public DecayingBlockEntity(BlockPos position, BlockState state, int decay) {
    super(ModBlockEntities.DECAYING.get(), position, state);
    this.decay = decay;
  }

  public static <T extends BlockEntity> void decayingTick (Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
    if (pBlockEntity instanceof DecayingBlockEntity entity) {
      if (pLevel != null && entity.decay-- <= 0) {
        pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 3);
      }
    }
  }

  @Override
  public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
    this.decay = tag.getInt("Decay");
    super.loadAdditional(tag, provider);
  }

  @Override
  public void saveAdditional(CompoundTag pCompound, HolderLookup.Provider provider  ) {
    super.saveAdditional(pCompound, provider);
    pCompound.putInt("Decay", this.decay);
  }
}
