package cn.enaium.fabric.imgui.mixin;

import com.mojang.blaze3d.platform.SDLEventHandler;
import cn.enaium.fabric.imgui.DefaultImGui;
import org.lwjgl.sdl.SDL_Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static cn.enaium.fabric.imgui.FabricImGui.IMGUI;

/**
 * @author Enaium
 */
@Mixin(SDLEventHandler.class)
public class SDLEventHandlerMixin {
    @Redirect(method = "pollEvents", at = @At(value = "INVOKE", target = "Lorg/lwjgl/sdl/SDL_Event;type()I"))
    public int pollEvents(SDL_Event instance) {
        final int type = instance.type();
        if (IMGUI.isCreated()) {
            DefaultImGui.imGuiImplSdl3.processEvent(instance.address());
        }
        return type;
    }
}
