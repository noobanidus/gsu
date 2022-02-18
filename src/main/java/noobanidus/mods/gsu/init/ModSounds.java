package noobanidus.mods.gsu.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.sounds.SoundEvent;

import static noobanidus.mods.gsu.GSU.REGISTRATE;

public class ModSounds {
  public static RegistryEntry<SoundEvent> CRUMBLE = REGISTRATE.soundEvent("crumbled_item").register();

  public static void load() {
  }
}
