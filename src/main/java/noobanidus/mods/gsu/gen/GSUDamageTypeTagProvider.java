package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class GSUDamageTypeTagProvider extends DamageTypeTagsProvider {

  public GSUDamageTypeTagProvider(PackOutput p_270719_, CompletableFuture<HolderLookup.Provider> p_270256_, @Nullable ExistingFileHelper existingFileHelper) {
    super(p_270719_, p_270256_, GSU.MODID, existingFileHelper);
  }

  @Override
  protected void addTags(HolderLookup.Provider p_270108_) {
    tag(GSUTags.Damage.CACTUS_DAMAGE).add(DamageTypes.CACTUS);
  }

  @Override
  public String getName() {
    return "GSU Damage Type Tags";
  }
}
