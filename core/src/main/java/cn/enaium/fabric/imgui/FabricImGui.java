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

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Enaium
 */
public class FabricImGui {
    public static final ImGuiService IMGUI;

    static {
        final Iterator<ImGuiService> iterator = ServiceLoader.load(ImGuiService.class).iterator();
        if (iterator.hasNext()) {
            IMGUI = iterator.next();
        } else {
            try {
                IMGUI = (ImGuiService) Class.forName("cn.enaium.fabric.imgui.DefaultImGui").getConstructor(String.class).newInstance((Object) null);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
