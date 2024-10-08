package noobanidus.mods.gsu.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.block.entity.DecayingBlockEntity;

public class ModBlockEntities {
  private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, GSU.MODID);

  public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DecayingBlockEntity>> DECAYING = REGISTER.register("decaying", () -> BlockEntityType.Builder.of(DecayingBlockEntity::new, ModBlocks.CRAWL.get()).build(null));

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }
}
