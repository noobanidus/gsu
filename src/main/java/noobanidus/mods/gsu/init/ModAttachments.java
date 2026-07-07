package noobanidus.mods.gsu.init;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.attachment.EntityEffect;
import noobanidus.mods.gsu.attachment.GlintType;

import java.util.function.Supplier;

public class ModAttachments {
  public static final ResourceLocation NO_SKIN = ResourceLocation.fromNamespaceAndPath(GSU.MODID, "no_skin");

  private static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GSU.MODID);

  public static final Supplier<AttachmentType<EntityEffect>> ENTITY_EFFECT = REGISTER.register("entity_effect", () -> AttachmentType.builder(() -> EntityEffect.NONE)
      .sync(EntityEffect.SYNC_HANDLER)
      .serialize(EntityEffect.CODEC).build());

  public static final Supplier<AttachmentType<ResourceLocation>> SKIN = REGISTER.register("skin", () -> AttachmentType.builder(() -> NO_SKIN)
      .serialize(ResourceLocation.CODEC).build());

  public static final Supplier<AttachmentType<Boolean>> PLAYER_HOSTILE = REGISTER.register("player_hostile", () -> AttachmentType.builder(() -> false)
      .serialize(Codec.BOOL).build());

  public static final Supplier<AttachmentType<Double>> HOSTILE_DAMAGE = REGISTER.register("player_hostile_damage", () -> AttachmentType.builder(() -> 2.0)
      .serialize(Codec.DOUBLE).build());

  public static final Supplier<AttachmentType<GlintType>> SHINY = REGISTER.register("shiny", () -> AttachmentType.builder(() -> GlintType.DEFAULT)
      .serialize(GlintType.CODEC).build());

  public static void register(IEventBus bus) {
    REGISTER.register(bus);
  }
}
