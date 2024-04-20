package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = GSU.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
  }
}
