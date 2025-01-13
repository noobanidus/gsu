package noobanidus.mods.gsu;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum GlintType implements StringRepresentable {
  DEFAULT("default"), BRIGHT_DEFAULT("bright_default"), GOLD("gold"), BRIGHT_GOLD("bright_gold"), LIME("lime"), BRIGHT_LIME("bright_lime"), CYAN("cyan"), BRIGHT_CYAN("bright_cyan");

  public static final IntFunction<GlintType> BY_ID = ByIdMap.continuous(GlintType::ordinal, GlintType.values(), ByIdMap.OutOfBoundsStrategy.ZERO);
  public static final Codec<GlintType> CODEC = StringRepresentable.fromEnum(GlintType::values);
  public static final StreamCodec<ByteBuf, GlintType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, GlintType::ordinal);

  private final String name;

  GlintType(String name) {
    this.name = name;
  }

  @Override
  public String getSerializedName() {
    return name;
  }

  public static GlintType fromString (String name) {
    for (GlintType type : GlintType.values()) {
      if (type.name.equals(name)) {
        return type;
      }
    }
    return DEFAULT;
  }
}
