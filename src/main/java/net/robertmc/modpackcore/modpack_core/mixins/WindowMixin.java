package net.robertmc.modpackcore.modpack_core.mixins;

import net.minecraft.client.util.Window;
import net.robertmc.modpackcore.modpack_core.ModpackDevClient;
import net.robertmc.modpackcore.modpack_core.SimpleConfig;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Window.class)
public abstract class WindowMixin {

    @Inject(method = "setTitle", at = @At("HEAD"), cancellable = true)
    private void setTitle(String title, CallbackInfo ci) {
        if (ModpackDevClient.docustomtitle) {
            String[] endingarray = title.split("-");
            String ending;
            if (endingarray.length == 2) {
                ending = endingarray[1].strip();
            } else{
                ending = "";
            }
            if (ModpackDevClient.customendingimpl){
                if (Objects.equals(ending, "")){
                    GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), ModpackDevClient.window_title);
                }
                else {
                    SimpleConfig x = ModpackDevClient.config;
                    x.config.put("ending", ending);
                    GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), ModpackDevClient.process(x.getOrDefault("core.window.title.endingstyle", "{core.window.title} - {ending}"), x));
                }
            } else {
                if (Objects.equals(ending, "")) {
                    GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), ModpackDevClient.window_title);
                } else {
                    GLFW.glfwSetWindowTitle(((Window) (Object) this).getHandle(), ModpackDevClient.window_title + " - " + ending);
                }
            }
            ci.cancel();
        }
    }
}
