package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import net.minecraft.world.level.block.entity.BlockEntity;
import noobanidus.mods.gsu.block.entity.DecayingBlockEntity;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModBlockEntities {
  public static final BlockEntityEntry<BlockEntity> DECAYING = REGISTRATE.blockEntity("decaying", DecayingBlockEntity::new).validBlock(ModBlocks.CRAWL).register();

  public static void load() {
  }
}
