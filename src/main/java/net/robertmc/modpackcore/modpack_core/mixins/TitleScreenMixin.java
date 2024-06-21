package net.robertmc.modpackcore.modpack_core.mixins;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.Text;
import net.robertmc.modpackcore.modpack_core.ModpackDevClient;
import net.robertmc.modpackcore.modpack_core.MyTextWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @Inject(at = @At("RETURN"), method = "init")
    public void onRender(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!Objects.equals(ModpackDevClient.bottomleft, "")) {
            Text x = Text.of(ModpackDevClient.bottomleft);
            if (Objects.equals(ModpackDevClient.bottomleftaction, "openurl")) {
                x = MyTextWidget.geturlopen(ModpackDevClient.bottomleft,ModpackDevClient.bottomleftdata);
            }
            int i = client.textRenderer.getWidth(x);
            ((TitleScreen) (Object) this).addDrawableChild(new MyTextWidget(2, client.currentScreen.height - 20, x, client.currentScreen,i));
        }
        if (!Objects.equals(ModpackDevClient.bottomright, "")) {
            Text x = Text.of(ModpackDevClient.bottomright);
            if (Objects.equals(ModpackDevClient.bottomrightaction, "openurl")) {
                x = MyTextWidget.geturlopen(ModpackDevClient.bottomright,ModpackDevClient.bottomrightdata);
            }
            int i = client.textRenderer.getWidth(x);
            ((TitleScreen) (Object) this).addDrawableChild(new MyTextWidget(client.currentScreen.width-i-2, client.currentScreen.height - 20, x, client.currentScreen,i));
        }
        if (!Objects.equals(ModpackDevClient.topleft, "")) {
            Text x = Text.of(ModpackDevClient.topleft);
            if (Objects.equals(ModpackDevClient.topleftaction, "openurl")) {
                x = MyTextWidget.geturlopen(ModpackDevClient.topleft,ModpackDevClient.topleftdata);
            }
            int i = client.textRenderer.getWidth(x);
            ((TitleScreen) (Object) this).addDrawableChild(new MyTextWidget(2, 2, x, client.currentScreen,i));
        }
        if (!Objects.equals(ModpackDevClient.topright, "")) {
            Text x = Text.of(ModpackDevClient.topright);
            if (Objects.equals(ModpackDevClient.toprightaction, "openurl")) {
                x = MyTextWidget.geturlopen(ModpackDevClient.topright,ModpackDevClient.toprightdata);
            }
            int i = client.textRenderer.getWidth(x);
            ((TitleScreen) (Object) this).addDrawableChild(new MyTextWidget(client.currentScreen.width-i-2, 2, x, client.currentScreen,i));
        }
    }
}