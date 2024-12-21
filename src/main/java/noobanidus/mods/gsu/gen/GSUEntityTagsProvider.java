package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GSUEntityTagsProvider extends EntityTypeTagsProvider {
  public GSUEntityTagsProvider(PackOutput p_256095_, CompletableFuture<HolderLookup.Provider> p_256572_, @Nullable ExistingFileHelper existingFileHelper) {
    super(p_256095_, p_256572_, GSU.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider p_255894_) {
    tag(GSUTags.Entity.RESKIN); // .add(EntityType.COW);
  }

  @Override
  public String getName() {
    return "GSU entity type tags";
  }
}
