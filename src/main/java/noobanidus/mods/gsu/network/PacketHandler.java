package noobanidus.mods.gsu.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import noobanidus.mods.gsu.GSU;

public class PacketHandler {

  public PacketHandler(IEventBus modEventBus) {
    modEventBus.addListener(RegisterPayloadHandlersEvent.class, event -> {
      PayloadRegistrar registrar = event.registrar(GSU.NETWORK_VERSION);
      registerClientToServer(new PacketRegistrar(registrar, true));
      registerServerToClient(new PacketRegistrar(registrar, false));
    });
  }

  protected void registerClientToServer(PacketRegistrar registrar) {
  }

  protected void registerServerToClient(PacketRegistrar registrar) {
    registrar.play(PacketSetSkin.TYPE, PacketSetSkin.STREAM_CODEC);
    registrar.play(PacketSetShiny.TYPE, PacketSetShiny.STREAM_CODEC);
  }

  protected record PacketRegistrar(PayloadRegistrar registrar, boolean toServer) {

    public <MSG extends ICustomPacket> void play(CustomPacketPayload.Type<MSG> type, StreamCodec<? super RegistryFriendlyByteBuf, MSG> reader) {
      if (toServer) {
        registrar.playToServer(type, reader, ICustomPacket::handle);
      } else {
        registrar.playToClient(type, reader, ICustomPacket::handle);
      }
    }
  }
}
