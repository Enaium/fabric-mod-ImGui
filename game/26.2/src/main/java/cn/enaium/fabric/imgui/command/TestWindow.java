package cn.enaium.fabric.imgui.command;

import cn.enaium.fabric.imgui.ImGuiRenderable;
import cn.enaium.fabric.imgui.TextureBinding;
import imgui.ImGui;
import imgui.ImGuiIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.data.AtlasIds;
import net.minecraft.network.chat.Component;

public class TestWindow extends Screen implements ImGuiRenderable {
    protected TestWindow(Component title) {
        super(title);
    }

    @Override
    public void render(ImGuiIO io) {
        ImGui.begin("ImGui TestWindow");

        Minecraft minecraft = Minecraft.getInstance();
        TextureAtlas atlas = minecraft.getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS);
        ImGui.image(TextureBinding.textureId(atlas.getTextureView()), 4096, 4096);

        ImGui.end();
    }
}
