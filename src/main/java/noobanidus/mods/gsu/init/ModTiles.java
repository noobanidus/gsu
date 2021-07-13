package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.tileentity.TileEntityType;
import noobanidus.mods.gsu.tiles.DecayingTileEntity;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModTiles {
  public static final RegistryEntry<TileEntityType<DecayingTileEntity>> DECAYING = REGISTRATE.tileEntity("decaying", DecayingTileEntity::new).validBlock(ModBlocks.CRAWL).register();

  public static void load() {
  }
}
