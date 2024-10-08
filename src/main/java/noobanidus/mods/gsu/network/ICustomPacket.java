package noobanidus.mods.gsu.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ICustomPacket extends CustomPacketPayload {
  void handle(IPayloadContext context);
}
