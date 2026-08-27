package cn.enaium.fabric.imgui;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public final class TextureBinding {
    private TextureBinding() {}

    private static final BiMap<GpuTextureView, Long> textureBindings = HashBiMap.create();

    private static final BiMap<GpuTextureView, Long> textureBindingsView = Maps.unmodifiableBiMap(textureBindings);

    private static long nextTextureId = 2; // ImGui reserves 1 for font texture

    public static BiMap<GpuTextureView, Long> getBindings() {
        return textureBindingsView;
    }

    /**
     * Register a texture for use with ImGui and get its ID
     * @param tex Texture object to use
     * @return The new or existing ID.
     */
    public static long textureId(@NonNull GpuTextureView tex) {
        RenderSystem.assertOnRenderThread();
        if (tex.texture() instanceof GlTexture glTexture) {
            return glTexture.glId();
        } else {
            return textureBindings.computeIfAbsent(Objects.requireNonNull(tex), _ -> nextTextureId++);
        }
    }

    /**
     * Dispose of all stale texture registrations
     */
    public static void clearStale() {
        RenderSystem.assertOnRenderThread();
        textureBindings.keySet().removeIf(GpuTextureView::isClosed);
    }

}
