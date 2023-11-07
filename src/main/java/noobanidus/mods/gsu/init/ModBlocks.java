package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import noobanidus.mods.gsu.block.CrawlBlock;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModBlocks {
  public static final RegistryEntry<CrawlBlock> CRAWL = REGISTRATE.block("crawl", CrawlBlock::new).properties(o -> o.randomTicks().strength(0, 0))
      .blockstate((ctx, p) -> p.simpleBlock(ctx.getEntry(), p.models().getExistingFile(new ResourceLocation("minecraft", "barrier")))).register();

  public static void load() {
  }
}
