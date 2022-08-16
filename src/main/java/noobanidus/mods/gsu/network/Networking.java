package noobanidus.mods.gsu.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import noobanidus.libs.noobutil.network.PacketHandler;
import noobanidus.mods.gsu.GSU;

public class Networking extends PacketHandler {
  public static Networking INSTANCE = new Networking();

  public Networking() {
    super(GSU.MODID);
  }

  @Override
  public void registerMessages() {
    registerMessage(SetSkin.class, SetSkin::encode, SetSkin::new, SetSkin::handle);
  }

  public static void sendTo(Object msg, ServerPlayer player) {
    INSTANCE.sendToInternal(msg, player);
  }

  public static void sendToServer(Object msg) {
    INSTANCE.sendToServerInternal(msg);
  }

  public static <MSG> void send(PacketDistributor.PacketTarget target, MSG message) {
    INSTANCE.sendInternal(target, message);
  }
}
