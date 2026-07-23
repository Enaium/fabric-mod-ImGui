/*
 * Copyright 2026 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.fabric.imgui;

import cn.enaium.fabric.imgui.blaze3d.ImGuiImplBlaze3D;
import cn.enaium.fabric.imgui.mixin.FrontendGpuDeviceMixin;
import cn.enaium.fabric.imgui.mixin.GlDeviceMixin;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.renderpearl.api.commands.CommandEncoder;
import com.mojang.renderpearl.api.commands.RenderPass;
import com.mojang.renderpearl.api.device.GpuDevice;
import com.mojang.renderpearl.backend.api.GpuDeviceBackend;
import com.mojang.renderpearl.backend.opengl.*;
import imgui.ImDrawData;
import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.sdl3.ImGuiImplSdl3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.PreferredGraphicsApi;
import org.jspecify.annotations.Nullable;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.sdl.SDLVideo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Enaium
 */
public class DefaultImGui extends ImGuiService {
    public static ImGuiImplSdl3 imGuiImplSdl3 = new ImGuiImplSdl3();
    public @Nullable ImGuiImplGl3 imGuiImplGl3;
    public @Nullable ImGuiImplBlaze3D imGuiImplBlaze3D;

    /**
     * @param id mod id
     */
    public DefaultImGui(String id) {
        super(id);
        if (Minecraft.getInstance().options.preferredGraphicsBackend().get() != PreferredGraphicsApi.VULKAN) {
            imGuiImplGl3 = new ImGuiImplGl3();
        } else {
            imGuiImplBlaze3D = new ImGuiImplBlaze3D();
        }
    }

    @Override
    public void init(final long handle) {
        if (imGuiImplGl3 != null) {
            imGuiImplGl3.init();
            imGuiImplSdl3.initForOpenGL(handle, 0);
        } else if (imGuiImplBlaze3D != null) {
            imGuiImplSdl3.initForVulkan(handle);
        }
        // ImGuiImplBlaze3D is lazily initialized in newFrame()
    }

    @Override
    public void draw(ImGuiRenderable renderable) {
        final RenderTarget framebuffer = Minecraft.getInstance().gameRenderer.mainRenderTarget();
        if (imGuiImplGl3 != null) {
            // OpenGL path: bind FBO, render ImGui, unbind FBO
            final GpuDeviceBackend backend = ((FrontendGpuDeviceMixin) RenderSystem.getDevice()).getBackend();
            final DirectStateAccess directStateAccess = ((GlDeviceMixin) backend).getDirectStateAccess();
            final FrameBufferCache frameBufferCache = ((GlDeviceMixin) backend).getFrameBufferCache();
            final List<FrameBufferAttachment> colorTextures = Collections.singletonList((GlTexture) framebuffer.getColorTexture());
            GlStateManager._glBindFramebuffer(GL30C.GL_FRAMEBUFFER, frameBufferCache.getFbo(directStateAccess, colorTextures, null));
            GL11C.glViewport(0, 0, framebuffer.width, framebuffer.height);

            imGuiImplGl3.newFrame();
            imGuiImplSdl3.newFrame();
            ImGui.newFrame();

            renderable.render(ImGui.getIO());

            ImGui.render();
            imGuiImplGl3.renderDrawData(ImGui.getDrawData());

            GlStateManager._glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
        } else if (imGuiImplBlaze3D != null) {
            // Blaze3D path: use CommandEncoder and RenderPass for GPU-agnostic rendering
            imGuiImplBlaze3D.newFrame();
            imGuiImplSdl3.newFrame();
            ImGui.newFrame();

            renderable.render(ImGui.getIO());

            ImGui.render();

            final ImDrawData drawData = ImGui.getDrawData();
            final GpuDevice device = RenderSystem.getDevice();
            final CommandEncoder encoder = device.createCommandEncoder();

            // Upload vertex, index, and uniform data before creating the render pass
            imGuiImplBlaze3D.uploadDrawData(drawData, encoder);

            // Create render pass on the framebuffer color texture and render ImGui
            try (RenderPass renderPass = encoder.createRenderPass(
                    () -> "ImGui",
                    framebuffer.getColorTextureView(),
                    Optional.empty())) {
                imGuiImplBlaze3D.renderDrawData(drawData, renderPass);
            }

            encoder.submit();
        }

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            long backupWindow = SDLVideo.SDL_GL_GetCurrentWindow();
            long backupContext = SDLVideo.SDL_GL_GetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();

            SDLVideo.SDL_GL_MakeCurrent(backupWindow, backupContext);
        }
    }

    @Override
    public void dispose() {
        if (imGuiImplGl3 != null) {
            imGuiImplGl3.shutdown();
        }
        if (imGuiImplBlaze3D != null) {
            imGuiImplBlaze3D.dispose();
        }
        imGuiImplSdl3.shutdown();
        super.dispose();
    }
}
