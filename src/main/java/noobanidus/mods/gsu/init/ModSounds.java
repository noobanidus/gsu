package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import noobanidus.mods.gsu.GSU;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModSounds {
  public static RegistryEntry<SoundEvent> CRUMBLE = REGISTRATE.simple("crumbled_item", Registries.SOUND_EVENT, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GSU.MODID, "crumbled_item")));

  public static void load() {
  }
}
