package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;

import java.util.concurrent.CompletableFuture;

public class GSUItemTagProvider extends ItemTagsProvider {
  public GSUItemTagProvider(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @org.jetbrains.annotations.Nullable ExistingFileHelper existingFileHelper) {
    super(p_275343_, p_275729_, p_275322_, GSU.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider provider) {
    tag(GSUTags.Item.CRUMBLE_BLACKLIST);
    tag(GSUTags.Item.FUMBLE_BLACKLIST);
  }

  @Override
  public String getName() {
    return "GSU Item Tags";
  }
}
