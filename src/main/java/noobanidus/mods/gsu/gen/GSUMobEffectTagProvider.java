package noobanidus.mods.gsu.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.init.ModEffects;

import java.util.concurrent.CompletableFuture;

public class GSUMobEffectTagProvider extends TagsProvider<MobEffect> {
  public GSUMobEffectTagProvider(PackOutput p_256012_, CompletableFuture<HolderLookup.Provider> p_256617_, @org.jetbrains.annotations.Nullable ExistingFileHelper existingFileHelper) {
    super(p_256012_, Registries.MOB_EFFECT, p_256617_, GSU.MODID, existingFileHelper);
  }

  protected void addTags(HolderLookup.Provider p_256206_) {
    this.tag(GSUTags.Potions.EFFECTS_PERSIST).add(ModEffects.ARMOR_SHIELD.getKey());
    this.tag(GSUTags.Potions.FIRE_EFFECT).add(ModEffects.INSTANT_FIRE.getKey()).add(ModEffects.DELAYED_FIRE.getKey());
    this.tag(GSUTags.Potions.SHADER_EFFECT).add(ModEffects.ENDERMAN.getKey(), ModEffects.CREEPER.getKey(), ModEffects.SPIDER.getKey());
    this.tag(GSUTags.Potions.MIRROR_CONTROLS).add(ModEffects.MIRROR.getKey(), ModEffects.MIRROR_CONTROLS.getKey());
    this.tag(GSUTags.Potions.MIRROR_VISUALS).add(ModEffects.MIRROR.getKey(), ModEffects.MIRROR_VISUALS.getKey());
  }
}
