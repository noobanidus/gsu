package noobanidus.mods.gsu.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import noobanidus.mods.gsu.GlintType;

public class CustomRenderType extends RenderType {
  public static ResourceLocation DEFAULT_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/default_entity_glint.png");
  public static ResourceLocation BRIGHT_DEFAULT_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/bright_default_entity_glint.png");
  public static ResourceLocation SUPER_BRIGHT_DEFAULT_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/super_bright_default_entity_glint.png");
  public static ResourceLocation GOLD_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/gold_entity_glint.png");
  public static ResourceLocation BRIGHT_GOLD_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/bright_gold_entity_glint.png");
  public static ResourceLocation SUPER_BRIGHT_GOLD_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/super_bright_gold_entity_glint.png");
  public static ResourceLocation LIME_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/lime_entity_glint.png");
  public static ResourceLocation BRIGHT_LIME_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/bright_lime_entity_glint.png");
  public static ResourceLocation SUPER_BRIGHT_LIME_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/super_bright_lime_entity_glint.png");
  public static ResourceLocation CYAN_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/cyan_entity_glint.png");
  public static ResourceLocation BRIGHT_CYAN_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/bright_cyan_entity_glint.png");
  public static ResourceLocation SUPER_BRIGHT_CYAN_ENTITY_GLINT = ResourceLocation.fromNamespaceAndPath("gsu", "textures/misc/glint/super_bright_entity_glint.png");

  private static final RenderStateShard.LayeringStateShard CUSTOM_POLYGON_OFFSET_LAYERING = new RenderStateShard.LayeringStateShard(
      "polygon_offset_layering", () -> {
    RenderSystem.polygonOffset(-0.25F, -10.0F);
    RenderSystem.enablePolygonOffset();
  }, () -> {
    RenderSystem.polygonOffset(0.0F, 0.0F);
    RenderSystem.disablePolygonOffset();
  }
  );
  public static final RenderType DEFAULT_GLINT = create(
      "default_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(DEFAULT_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static final RenderType BRIGHT_DEFAULT_GLINT = create(
      "bright_default_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(BRIGHT_DEFAULT_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );
  public static final RenderType SUPER_BRIGHT_DEFAULT_GLINT = create(
      "super_bright_default_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(SUPER_BRIGHT_DEFAULT_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static final RenderType GOLD_GLINT = create(
      "gold_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(GOLD_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static final RenderType BRIGHT_GOLD_GLINT = create(
      "bright_gold_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(BRIGHT_GOLD_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );
  public static final RenderType SUPER_BRIGHT_GOLD_GLINT = create(
      "super_bright_gold_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(SUPER_BRIGHT_GOLD_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static final RenderType CYAN_GLINT = create(
      "cyan_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(CYAN_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static final RenderType BRIGHT_CYAN_GLINT = create(
      "bright_cyan_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(BRIGHT_CYAN_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );
  public static final RenderType SUPER_BRIGHT_CYAN_GLINT = create(
      "super_bright_cyan_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(SUPER_BRIGHT_CYAN_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );


  public static final RenderType LIME_GLINT = create(
      "lime_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(LIME_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static final RenderType BRIGHT_LIME_GLINT = create(
      "bright_lime_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(BRIGHT_LIME_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );
  public static final RenderType SUPER_BRIGHT_LIME_GLINT = create(
      "super_bright_lime_glint",
      DefaultVertexFormat.POSITION_TEX,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
          .setTextureState(new RenderStateShard.TextureStateShard(SUPER_BRIGHT_LIME_ENTITY_GLINT, true, false))
          .setWriteMaskState(COLOR_WRITE)
          .setCullState(NO_CULL)
          .setDepthTestState(LEQUAL_DEPTH_TEST)
          .setTransparencyState(GLINT_TRANSPARENCY)
          .setTexturingState(GLINT_TEXTURING)
          .setLayeringState(CUSTOM_POLYGON_OFFSET_LAYERING)
          .createCompositeState(false)
  );

  public static RenderType getByGlint(GlintType glint) {
    return switch (glint) {
      case GlintType.SUPER_BRIGHT_CYAN -> SUPER_BRIGHT_CYAN_GLINT;
      case GlintType.SUPER_BRIGHT_LIME -> SUPER_BRIGHT_LIME_GLINT;
      case GlintType.SUPER_BRIGHT_GOLD -> SUPER_BRIGHT_GOLD_GLINT;
      case GlintType.SUPER_BRIGHT_DEFAULT -> SUPER_BRIGHT_DEFAULT_GLINT;
      case GlintType.BRIGHT_CYAN -> BRIGHT_CYAN_GLINT;
      case GlintType.CYAN -> CYAN_GLINT;
      case GlintType.BRIGHT_LIME -> BRIGHT_LIME_GLINT;
      case GlintType.LIME -> LIME_GLINT;
      case GlintType.GOLD -> GOLD_GLINT;
      case GlintType.BRIGHT_GOLD -> BRIGHT_GOLD_GLINT;
      case GlintType.BRIGHT_DEFAULT -> BRIGHT_DEFAULT_GLINT;
      default -> DEFAULT_GLINT;
    };
  }

  private CustomRenderType(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
    super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    throw new UnsupportedOperationException();
  }
}
