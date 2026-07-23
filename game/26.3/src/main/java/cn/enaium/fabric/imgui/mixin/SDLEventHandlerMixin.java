package cn.enaium.fabric.imgui.mixin;

import cn.enaium.fabric.imgui.DefaultImGui;
import com.mojang.blaze3d.platform.SDLEventHandler;
import org.lwjgl.sdl.SDL_Event;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * @author Enaium
 */
@Mixin(SDLEventHandler.class)
public class SDLEventHandlerMixin {
    @Redirect(method = "pollEvents", at = @At(value = "INVOKE", target = "Lorg/lwjgl/sdl/SDL_Event;type()I"))
    public int pollEvents(SDL_Event instance) {
        final int type = instance.type();
        DefaultImGui.imGuiImplSdl3.processEvent(instance.address());
        return type;
    }
}
