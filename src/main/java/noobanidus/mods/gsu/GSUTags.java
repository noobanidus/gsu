package noobanidus.mods.gsu;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class GSUTags {
  public static class Blocks {

  }

  public static class Entity {
    public static TagKey<EntityType<?>> RESKIN = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "gsu_reskin"));
  }

  public static class Potions {
    public static TagKey<MobEffect> EFFECTS_PERSIST = TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "effects_persist"));
    public static TagKey<MobEffect> SUPPRESS_PARTICLES = TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "suppress_particles"));
  }
}
