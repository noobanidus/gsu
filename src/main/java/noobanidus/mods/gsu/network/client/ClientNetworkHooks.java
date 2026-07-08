package noobanidus.mods.gsu.network.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.EntitySpectatorShaderManager;
import noobanidus.mods.gsu.GSUTags;
import noobanidus.mods.gsu.attachment.EntityEffect;
import noobanidus.mods.gsu.attachment.GlintType;
import noobanidus.mods.gsu.init.ModAttachments;

public class ClientNetworkHooks {
  public static void setSkin(int entityId, ResourceLocation skin) {
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

  public static void setShiny(int entityId, GlintType glint) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft == null || minecraft.level == null) {
      return;
    }
    Entity entity = minecraft.level.getEntity(entityId);
    if (entity == null) {
      return;
    }
    entity.setData(ModAttachments.SHINY, glint);
  }

  public static final ResourceLocation CREEPER = ResourceLocation.withDefaultNamespace("shaders/post/creeper.json");
  public static final ResourceLocation SPIDER = ResourceLocation.withDefaultNamespace("shaders/post/spider.json");
  public static final ResourceLocation ENDERMAN = ResourceLocation.withDefaultNamespace("shaders/post/invert.json");

  public static void tryRefreshShader () {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft == null || minecraft.player == null) {
      return;
    }

    tryRefreshShader(minecraft.player.getData(ModAttachments.ENTITY_EFFECT));
  }

  public static void tryRefreshShader(EntityEffect effect) {
    Minecraft minecraft = Minecraft.getInstance();
    if (minecraft == null) {
      return;
    }

    if (!effect.exists()) {
      // Null here disables the shader
      if (minecraft.getCameraEntity() != minecraft.player) {
        minecraft.gameRenderer.checkEntityPostEffect(minecraft.getCameraEntity());
      } else {
        minecraft.gameRenderer.checkEntityPostEffect(null);
      }
    } else {
      ResourceLocation shader;
      if (effect.is(EntityType.CREEPER)) {
        shader = CREEPER;
      } else if (effect.is(EntityType.SPIDER) || effect.is(EntityType.CAVE_SPIDER)) {
        shader = SPIDER;
      } else if (effect.is(EntityType.ENDERMAN)) {
        shader = ENDERMAN;
      } else {
        shader = EntitySpectatorShaderManager.get(effect.resolve());
      }
      if (shader != null) {
        minecraft.gameRenderer.loadEffect(shader);
      }
    }
  }
}
