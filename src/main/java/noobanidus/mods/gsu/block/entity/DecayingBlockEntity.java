package noobanidus.mods.gsu.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.libs.noobutil.reference.NBTConstants;
import noobanidus.libs.noobutil.type.LazyStateSupplier;

public class DecayingBlockEntity extends BlockEntity {
  private int decay;
  private LazyStateSupplier state;

  public DecayingBlockEntity(BlockEntityType<?> type, BlockPos position, BlockState state) {
    this(type, position, state, new LazyStateSupplier(Blocks.AIR.defaultBlockState()), 35);
  }

  public DecayingBlockEntity(BlockEntityType<?> type, BlockPos position, BlockState state, LazyStateSupplier block, int decay) {
    super(type, position, state);
    this.decay = decay;
    this.state = block;
  }

  public static <T extends BlockEntity> void decayingTick (Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
    if (pBlockEntity instanceof DecayingBlockEntity entity) {
      if (pLevel != null && entity.decay-- <= 0) {
        pLevel.setBlock(pPos, entity.state.get(), 3);
      }
    }
  }

  @Override
  public void load(CompoundTag tag) {
    this.decay = tag.getInt(NBTConstants.DecayingBlockEntity.Decay);
    this.state = LazyStateSupplier.fromNBT(tag.getCompound(NBTConstants.DecayingBlockEntity.State));
    super.load(tag);
  }

  @Override
  public void saveAdditional(CompoundTag pCompound) {
    super.saveAdditional(pCompound);
    pCompound.putInt(NBTConstants.DecayingBlockEntity.Decay, this.decay);
    pCompound.put(NBTConstants.DecayingBlockEntity.State, this.state.serializeNBT());
  }
}
