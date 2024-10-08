package noobanidus.mods.gsu.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModBlocks;
import noobanidus.mods.gsu.init.ModEffects;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class GSULanguageProvider extends LanguageProvider {
  public GSULanguageProvider(PackOutput output, String locale) {
    super(output, GSU.MODID, locale);
  }

  @Override
  protected void addTranslations() {
    addBlock(ModBlocks.CRAWL, getDescription(ModBlocks.CRAWL));
    addEffect(ModEffects.CACTUS_SHIELD, getDescription(ModEffects.CACTUS_SHIELD));
    addEffect(ModEffects.IMMORTAL, getDescription(ModEffects.IMMORTAL));
    addEffect(ModEffects.IMMORTAL_DYING, getDescription(ModEffects.IMMORTAL_DYING));
    addEffect(ModEffects.DYING, getDescription(ModEffects.DYING));
    addEffect(ModEffects.KNOCKUP, getDescription(ModEffects.KNOCKUP));
    addEffect(ModEffects.KNOCKBACK, getDescription(ModEffects.KNOCKBACK));
    addEffect(ModEffects.ARMOR_SHIELD, getDescription(ModEffects.ARMOR_SHIELD));
    addEffect(ModEffects.CRUMBLE, getDescription(ModEffects.CRUMBLE));
    addEffect(ModEffects.DELAYED_EXPLOSIVE, getDescription(ModEffects.DELAYED_EXPLOSIVE));
    addEffect(ModEffects.DELAYED_FIRE, getDescription(ModEffects.DELAYED_FIRE));
    addEffect(ModEffects.DRUMBLE, getDescription(ModEffects.DRUMBLE));
    addEffect(ModEffects.EXPLOSIVE, getDescription(ModEffects.EXPLOSIVE));
    addEffect(ModEffects.FUMBLE, getDescription(ModEffects.FUMBLE));
    addEffect(ModEffects.INSTANT_FIRE, getDescription(ModEffects.INSTANT_FIRE));
    addEffect(ModEffects.JUMBLE, getDescription(ModEffects.JUMBLE));
    addEffect(ModEffects.STUMBLE, getDescription(ModEffects.STUMBLE));
    addEffect(ModEffects.THIMBLE, getDescription(ModEffects.THIMBLE));
    addEffect(ModEffects.TUMBLE, getDescription(ModEffects.TUMBLE));


  }

  private String getDescription(DeferredHolder<?, ?> holder) {
    return Arrays.stream(holder.getKey().location().getPath().toLowerCase(Locale.ROOT).split("_")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
  }
}
