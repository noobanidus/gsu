package noobanidus.mods.gsu.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModEffects;

@Mod.EventBusSubscriber(modid = GSU.MODID, value = Dist.CLIENT)
public class ClientEventsHandler {
  @SubscribeEvent
  public static void tumbleHandler(EntityViewRenderEvent.CameraSetup event) {
    Player player = Minecraft.getInstance().player;
    if (player != null && player.getEffect(ModEffects.TUMBLE.get()) != null) {
      event.setRoll(180f);
    }
  }

  @SubscribeEvent
  public static void onInput(MovementInputUpdateEvent event) {
    if (!event.getPlayer().hasEffect(ModEffects.JUMBLE.get())) {
      return;
    }

    Input input = event.getInput();
    boolean forwardKeyDown = input.up;
    boolean backKeyDown = input.down;
    boolean leftKeyDown = input.left;
    boolean rightKeyDown = input.right;
    boolean jump = input.jumping;
    boolean sneaking = input.shiftKeyDown;

    input.up = backKeyDown;
    input.down = forwardKeyDown;
    input.left = rightKeyDown;
    input.right = leftKeyDown;
    input.shiftKeyDown = jump;
    input.jumping = sneaking;
    input.forwardImpulse = input.up == input.down ? 0.0F : (input.up ? 1.0F : -1.0F);
    input.leftImpulse = input.left == input.right ? 0.0F : (input.left ? 1.0F : -1.0F);
  }
}
