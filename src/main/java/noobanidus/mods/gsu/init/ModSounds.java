package noobanidus.mods.gsu.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.gsu.GSU;

import java.util.function.Supplier;

public class ModSounds {
  private static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, GSU.MODID);

  public static final Supplier<SoundEvent> CRUMBLE = REGISTER.register("crumbled_item", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(GSU.MODID, "crumbled_item")));

  public static void register (IEventBus bus) {
    REGISTER.register(bus);
  }
}
