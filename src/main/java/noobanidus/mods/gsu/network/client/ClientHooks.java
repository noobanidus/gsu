package noobanidus.mods.gsu.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.init.ModAttachments;

public class ClientHooks {
  public static void setSkin (int entityId, ResourceLocation skin) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft == null || minecraft.level == null) {
      return;
    }
    Entity entity = minecraft.level.getEntity(entityId);
    if (entity == null) {
      return;
    }
    if (entity.getType().is(GSUTags.Entity.RESKIN)) {
      entity.setData(ModAttachments.SKIN, skin);
    }
  }
}
