package noobanidus.mods.gsu.network;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import noobanidus.mods.gsu.network.client.ClientHooks;

public record PacketSetSkin(int entityId, ResourceLocation skin) implements ICustomPacket {
  public static final CustomPacketPayload.Type<PacketSetSkin> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("gsu", "set_skin"));

  public static final StreamCodec<ByteBuf, PacketSetSkin> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, PacketSetSkin::entityId, ResourceLocation.STREAM_CODEC, PacketSetSkin::skin, PacketSetSkin::new);

  @Override
  public void handle(IPayloadContext context) {
    ClientHooks.setSkin(this.entityId(), this.skin());
  }

  @Override
  public Type<? extends CustomPacketPayload> type() {
    return TYPE;
  }
}
