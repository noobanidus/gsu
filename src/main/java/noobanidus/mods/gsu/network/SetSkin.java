package noobanidus.mods.gsu.network;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;
import noobanidus.mods.gsu.capability.SkinCapabilityProvider;
import noobanidus.mods.gsu.config.ConfigManager;

import java.util.function.Supplier;

public class SetSkin {
  private int entityId;
  private ResourceLocation skin;

  public SetSkin(PacketBuffer buffer) {
    this.entityId = buffer.readVarInt();
    this.skin = buffer.readResourceLocation();
  }

  public SetSkin(int entityId, ResourceLocation skin) {
    this.entityId = entityId;
    this.skin = skin;
  }

  public void encode(PacketBuffer buf) {
    buf.writeVarInt(this.entityId);
    buf.writeResourceLocation(this.skin);
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    context.get().enqueueWork(() -> handle(this, context));
  }

  @OnlyIn(Dist.CLIENT)
  private static void handle(SetSkin message, Supplier<NetworkEvent.Context> context) {
    PlayerEntity target = Minecraft.getInstance().player;
    if (target == null) {
      return;
    }
    World world = target.level;
    Entity entity = world.getEntity(message.entityId);
    if (entity != null && ConfigManager.getEntitySet().contains(entity.getType())) {
      entity.getCapability(SkinCapabilityProvider.SKIN_CAPABILITY).ifPresent(cap -> {
        cap.setOverride(message.skin);
      });
    }

    context.get().setPacketHandled(true);
  }
}

