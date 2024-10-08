package noobanidus.mods.gsu.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.block.CrawlBlock;

public class ModBlocks {
  private static final DeferredRegister<Block> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK, GSU.MODID);

  public static final DeferredHolder<Block, CrawlBlock> CRAWL = REGISTER.register("crawl", () -> new CrawlBlock(BlockBehaviour.Properties.of().strength(0, 0).randomTicks()));

  public static void register (IEventBus bus) {
    REGISTER.register(bus);
  }
}
