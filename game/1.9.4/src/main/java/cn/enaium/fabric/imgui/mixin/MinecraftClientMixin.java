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

package cn.enaium.fabric.imgui.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static cn.enaium.fabric.imgui.FabricImGui.IMGUI;

/**
 * @author Enaium
 */
@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(method = "initializeGame", at = @At("RETURN"))
    public void initImGui(CallbackInfo ci) {
        IMGUI.create(-1);
    }

    @Inject(method = "stop", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundManager;close()V"))
    public void closeImGui(CallbackInfo ci) {
        IMGUI.dispose();
    }
}
