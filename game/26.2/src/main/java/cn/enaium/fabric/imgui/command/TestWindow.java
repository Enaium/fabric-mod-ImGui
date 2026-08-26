package cn.enaium.fabric.imgui.command;

import cn.enaium.fabric.imgui.ImGuiRenderable;
import imgui.ImGui;
import imgui.ImGuiIO;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class TestWindow extends Screen implements ImGuiRenderable {
    protected TestWindow(Component title) {
        super(title);
    }

    @Override
    public void render(ImGuiIO io) {
        ImGui.showDemoWindow();
    }
}
