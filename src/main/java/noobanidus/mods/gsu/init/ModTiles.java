package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.TileEntityEntry;
import net.minecraft.tileentity.TileEntity;
import noobanidus.libs.noobutil.tiles.DecayingTileEntity;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModTiles {
  public static final TileEntityEntry<TileEntity> DECAYING = REGISTRATE.tileEntity("decaying", DecayingTileEntity::new).validBlock(ModBlocks.CRAWL).register();

  public static void load() {
  }
}
