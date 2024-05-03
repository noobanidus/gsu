package noobanidus.mods.gsu;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public class GSUTags {
  public static class Blocks {

  }

  public static class Items {
    public static TagKey<Item> NBT_CHECK = TagKey.create(Registries.ITEM, new ResourceLocation(GSU.MODID, "nbt_check"));
  }

  public static class Potions {
    public static TagKey<MobEffect> EFFECTS_PERSIST = TagKey.create(Registries.MOB_EFFECT, new ResourceLocation(GSU.MODID, "effects_persist"));
  }
}
