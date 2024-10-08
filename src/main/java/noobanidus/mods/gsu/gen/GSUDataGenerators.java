package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import noobanidus.mods.gsu.GSU;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GSU.MODID, bus = EventBusSubscriber.Bus.MOD)
public class GSUDataGenerators {
  @SubscribeEvent
  public static void gatherData(GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    PackOutput output = event.getGenerator().getPackOutput();
    CompletableFuture<HolderLookup.Provider> provider = event.getLookupProvider();
    ExistingFileHelper helper = event.getExistingFileHelper();

    GSUBlockTagProvider blocks = new GSUBlockTagProvider(output, provider, helper);
    generator.addProvider(event.includeServer(), blocks);
    generator.addProvider(event.includeServer(), new GSUItemTagProvider(output, provider,blocks.contentsGetter(), helper));
    generator.addProvider(event.includeServer(), new GSUMobEffectTagProvider(output, provider, helper));
  }
}
