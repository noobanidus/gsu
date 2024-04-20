package noobanidus.mods.gsu;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class GSUTags {
  public static class Blocks {

  }

  public static class Items {
    public static TagKey<Item> NBT_CHECK = TagKey.create(Registries.ITEM, new ResourceLocation(GSU.MODID, "nbt_check"));
  }
}
