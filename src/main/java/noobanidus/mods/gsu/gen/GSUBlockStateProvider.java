package noobanidus.mods.gsu.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModBlocks;

public class GSUBlockStateProvider extends BlockStateProvider {
  public GSUBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, GSU.MODID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    getVariantBuilder(ModBlocks.CRAWL.get()).forAllStates(o -> new ConfiguredModel[]{new ConfiguredModel(models().getExistingFile(mcLoc("block/barrier")))});
  }
}
