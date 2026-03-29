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

package cn.enaium.fabric.imgui.lwjgl;

import imgui.glfw.ImGuiImplGlfw;
import org.lwjgl.PointerBuffer;
import org.lwjgl.Version;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.glfwGetError;

/**
 * @author Enaium
 */
public class MyImGuiImplGlfw extends ImGuiImplGlfw {
    @Override
    protected void eatErrors() {
        if (Version.getVersion().startsWith("3.2.")) {
            if (glfwHasGetError) {
                final PointerBuffer pb = MemoryUtil.memAllocPointer(1);
                glfwGetError(pb);
                MemoryUtil.memFree(pb);
            }
        } else {
            super.eatErrors();
        }
    }
}
