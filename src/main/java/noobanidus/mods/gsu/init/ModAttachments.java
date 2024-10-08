package noobanidus.mods.gsu.init;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import noobanidus.mods.gsu.GSU;

import java.util.function.Supplier;

public class ModAttachments {
  public static final ResourceLocation NO_SKIN = ResourceLocation.fromNamespaceAndPath(GSU.MODID, "no_skin");

  private static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, GSU.MODID);

  public static final Supplier<AttachmentType<ResourceLocation>> SKIN = REGISTER.register("skin", () -> AttachmentType.builder(() -> NO_SKIN).serialize(ResourceLocation.CODEC).build());

  public static void register (IEventBus bus) {
    REGISTER.register(bus);
  }
}
