package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.gsu.GSU;

import java.util.concurrent.CompletableFuture;

public class GSUBlockTagProvider extends BlockTagsProvider {
  public GSUBlockTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, @org.jetbrains.annotations.Nullable ExistingFileHelper existingFileHelper) {
    super(p_275343_, p_275729_, GSU.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
  }

  @Override
  public String getName() {
    return "GSU Block Tags";
  }
}
