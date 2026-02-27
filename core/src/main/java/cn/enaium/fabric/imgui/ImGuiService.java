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
import imgui.ImGuiIO;
import imgui.extension.implot.ImPlot;
import imgui.flag.ImGuiConfigFlags;

/**
 * @author Enaium
 */
public abstract class ImGuiService {
    private final String id;

    public ImGuiService() {
        this(null);
    }

    /**
     * @param id mod id
     */
    public ImGuiService(String id) {
        this.id = id;
    }

    /**
     * Create context
     *
     * @param handle window handle
     */
    public void create(final long handle) {
        ImGui.createContext();
        ImPlot.createContext();

        final ImGuiIO data = ImGui.getIO();
        configure(data);
        init(handle);
    }

    public abstract void init(final long handle);

    public void configure(final ImGuiIO data) {
        data.getFonts().addFontDefault();
        data.getFonts().build();
        if (id != null) {
            data.setIniFilename(id + ".ini");
        } else {
            data.setIniFilename(null);
        }
        data.setConfigFlags(ImGuiConfigFlags.DockingEnable);
    }

    public abstract void draw(ImGuiRenderable renderable);

    /**
     * Dispose context
     */
    public void dispose() {
        ImPlot.destroyContext();
        ImGui.destroyContext();
    }
}
