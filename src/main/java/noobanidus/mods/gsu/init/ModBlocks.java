package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import noobanidus.mods.gsu.blocks.CrawlBlock;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModBlocks {
  public static final RegistryEntry<CrawlBlock> CRAWL = REGISTRATE.block("crawl", Material.ANVIL, CrawlBlock::new).properties(o -> o.tickRandomly().hardnessAndResistance(0, 0))
      .blockstate((ctx, p) -> p.simpleBlock(ctx.getEntry(), p.models().getExistingFile(new ResourceLocation("minecraft", "barrier")))).register();

  public static void load() {
  }
}
