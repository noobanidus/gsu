package noobanidus.mods.gsu.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import noobanidus.mods.gsu.attachment.GlintType;
import noobanidus.mods.gsu.network.client.ClientNetworkHooks;

public record PacketSetShiny(int entityId, GlintType glint) implements ICustomPacket {
  public static final CustomPacketPayload.Type<PacketSetShiny> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("gsu", "set_shiny"));

  public static final StreamCodec<ByteBuf, PacketSetShiny> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, PacketSetShiny::entityId, GlintType.STREAM_CODEC, PacketSetShiny::glint, PacketSetShiny::new);


  @Override
  public void handle(IPayloadContext context) {
    ClientNetworkHooks.setShiny(this.entityId(), this.glint());
  }

  @Override
  public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
