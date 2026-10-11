package noobanidus.mods.gsu.network;


import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.capability.GlintType;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.function.Supplier;

public class SetGlint {
  private int entityId;
  private GlintType glint;

  public SetGlint(FriendlyByteBuf buffer) {
    this.entityId = buffer.readVarInt();
    this.glint = GlintType.fromString(buffer.readUtf());
  }

  public SetGlint(int entityId, GlintType glint) {
    this.entityId = entityId;
    this.glint = glint;
  }

  public void encode(FriendlyByteBuf buf) {
    buf.writeVarInt(this.entityId);
    buf.writeUtf(this.glint.getSerializedName());
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> handle(this, context));
  }

  @OnlyIn(Dist.CLIENT)
  private static void handle(SetGlint message, Supplier<NetworkEvent.Context> context) {
    Player target = Minecraft.getInstance().player;
    if (target == null) {
      return;
    }
    Level world = target.level();
    Entity entity = world.getEntity(message.entityId);
    if (entity != null && ConfigManager.getEntitySet().contains(entity.getType())) {
      entity.getCapability(Capabilities.SHINY_CAPABILITY).ifPresent(cap -> {
        cap.setGlint(message.glint);
      });
    }

    context.get().setPacketHandled(true);
  }
}

