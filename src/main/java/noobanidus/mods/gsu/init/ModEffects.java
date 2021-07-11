package noobanidus.mods.timetodie.init;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.potion.Effect;
import noobanidus.mods.timetodie.TimeToDie;
import noobanidus.mods.timetodie.effects.DyingEffect;

public class ModEffects {
  public static final RegistryEntry<DyingEffect> DYING = TimeToDie.REGISTRATE.effect("dying", DyingEffect::new).register();

  public static void load() {
  }
}
