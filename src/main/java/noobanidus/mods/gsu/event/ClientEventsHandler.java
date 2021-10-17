package noobanidus.mods.gsu.event;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MovementInput;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import noobanidus.mods.gsu.GSU;
import noobanidus.mods.gsu.init.ModEffects;

@Mod.EventBusSubscriber(modid = GSU.MODID, value = Dist.CLIENT)
public class ClientEventsHandler {
  @SubscribeEvent
  public static void tumbleHandler(EntityViewRenderEvent.CameraSetup event) {
    PlayerEntity player = Minecraft.getInstance().player;
    if (player != null && player.getEffect(ModEffects.TUMBLE.get()) != null) {
      event.setRoll(180f);
    }
  }

  @SubscribeEvent
  public static void onInput(InputUpdateEvent event) {
    if (!event.getPlayer().hasEffect(ModEffects.JUMBLE.get())) {
      return;
    }

    MovementInput input = event.getMovementInput();
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
