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

import imgui.ImGui;
import imgui.flag.ImGuiConfigFlags;
import loutre.imgui.lwjgl2.ImGuiDisplay;
import loutre.imgui.lwjgl2.ImGuiLWJGL2;

/**
 * @author Enaium
 */
public class DefaultImGui extends ImGuiService {
    public final static ImGuiDisplay imGuiDisplay = new ImGuiDisplay();
    public final static ImGuiLWJGL2 imGuiImplGl2 = new ImGuiLWJGL2();

    /**
     * @param id mod id
     */
    public DefaultImGui(String id) {
        super(id);
    }

    @Override
    public void init(long handle) {
        imGuiDisplay.init();
        imGuiImplGl2.init();
    }

    @Override
    public void draw(ImGuiRenderable renderable) {
        imGuiDisplay.newFrame();
        ImGui.newFrame();
        renderable.render(ImGui.getIO());
        ImGui.render();

        imGuiImplGl2.newFrame();
        imGuiImplGl2.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
        }
    }
}
