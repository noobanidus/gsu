package noobanidus.mods.gsu.network;


import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import noobanidus.mods.gsu.capability.Capabilities;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.function.Supplier;

public class SetSkin {
  private int entityId;
  private ResourceLocation skin;

  public SetSkin(FriendlyByteBuf buffer) {
    this.entityId = buffer.readVarInt();
    this.skin = buffer.readResourceLocation();
  }

  public SetSkin(int entityId, ResourceLocation skin) {
    this.entityId = entityId;
    this.skin = skin;
  }

  public void encode(FriendlyByteBuf buf) {
    buf.writeVarInt(this.entityId);
    buf.writeResourceLocation(this.skin);
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> handle(this, context));
  }

  @OnlyIn(Dist.CLIENT)
  private static void handle(SetSkin message, Supplier<NetworkEvent.Context> context) {
    Player target = Minecraft.getInstance().player;
    if (target == null) {
      return;
    }
    Level world = target.level();
    Entity entity = world.getEntity(message.entityId);
    if (entity != null && ConfigManager.getEntitySet().contains(entity.getType())) {
      entity.getCapability(Capabilities.SKIN_CAPABILITY).ifPresent(cap -> {
        cap.setOverride(message.skin);
      });
    }

    context.get().setPacketHandled(true);
  }
}

