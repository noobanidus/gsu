package noobanidus.mods.gsu.attachment;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import noobanidus.mods.gsu.network.client.ClientNetworkHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public record EntityEffect(ResourceKey<EntityType<?>> type) {
  public static final EntityEffect NONE = new EntityEffect(EntityType.AREA_EFFECT_CLOUD);
  public static final StreamCodec<ByteBuf, EntityEffect> STREAM_CODEC = StreamCodec.composite(ResourceLocation.STREAM_CODEC, o -> o.type.location(), EntityEffect::new);
  public static final Codec<EntityEffect> CODEC = ResourceLocation.CODEC.xmap(EntityEffect::new, EntityEffect::location);
  public static final AttachmentSyncHandler<EntityEffect> SYNC_HANDLER = new AttachmentSyncHandler<EntityEffect>() {
    @Override
    public void write(RegistryFriendlyByteBuf buf, EntityEffect attachment, boolean initialSync) {
      STREAM_CODEC.encode(buf, attachment);
    }

    @Override
    public EntityEffect read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, EntityEffect previousValue) {
      // This is how EntityEffects are applied
      var result = STREAM_CODEC.decode(buf);
      if (result != previousValue) {
        ClientNetworkHooks.tryRefreshShader(result);
      }
      return result;
    }
  };

  public EntityEffect(EntityType<?> entity) {
    this(BuiltInRegistries.ENTITY_TYPE.getKey(entity));
  }

  public EntityEffect(ResourceLocation entity) {
    this(ResourceKey.create(Registries.ENTITY_TYPE, entity));
  }

  @NotNull
  private ResourceLocation location() {
    return type.location();
  }

  @NotNull
  public EntityType<?> resolve() {
    var result = BuiltInRegistries.ENTITY_TYPE.get(type);
    if (result == null) {
      throw new NullPointerException("Entity type " + type + " is not registered!");
    }
    return result;
  }

  public boolean exists() {
    return !this.type.equals(NONE.type());
  }

  public boolean is (EntityType<?> type) {
    return type.builtInRegistryHolder().is(this.type());
  }

  public boolean is (EntityEffect effect) {
    return this == effect;
  }

  public boolean is (ResourceLocation location) {
    return type.location().equals(location);
  }
}
