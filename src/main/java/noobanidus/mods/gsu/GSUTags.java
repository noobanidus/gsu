package noobanidus.mods.gsu;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class GSUTags {
  public static class Block {

  }

  public static class Item {
    public static TagKey<net.minecraft.world.item.Item> CRUMBLE_BLACKLIST = ItemTags.create(ResourceLocation.fromNamespaceAndPath(GSU.MODID, "crumble_blacklist"));
    public static TagKey<net.minecraft.world.item.Item> FUMBLE_BLACKLIST = ItemTags.create(ResourceLocation.fromNamespaceAndPath(GSU.MODID, "fumble_blacklist"));
  }

  public static class Entity {
    public static TagKey<EntityType<?>> RESKIN = TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "gsu_reskin"));
  }

  public static class Potions {
    public static TagKey<MobEffect> EFFECTS_PERSIST = TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "effects_persist"));
    public static TagKey<MobEffect> SUPPRESS_PARTICLES = TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "suppress_particles"));
    public static TagKey<MobEffect> FIRE_EFFECT = TagKey.create(Registries.MOB_EFFECT, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "fire_effect"));
  }

  public static class Damage {
    public static TagKey<DamageType> CACTUS_DAMAGE = TagKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(GSU.MODID, "cactus_damage"));
  }
}
