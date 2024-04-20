package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.BlockTagsProvider;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;

import java.util.concurrent.CompletableFuture;

public class GSUBlockTagProvider extends BlockTagsProvider {
  public GSUBlockTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, @org.jetbrains.annotations.Nullable net.minecraftforge.common.data.ExistingFileHelper existingFileHelper) {
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
