package net.robertmc.modpackcore.modpack_core;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PressableTextWidget;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.robertmc.modpackcore.modpack_core.mixins.TextAccessor;

@Environment(EnvType.CLIENT)
public class MyTextWidget extends PressableTextWidget {
    private final boolean clickable;

    public MyTextWidget(int x, int y, Text text, Screen screen, int width) {
        super(x, y, MinecraftClient.getInstance().textRenderer.getWidth(text), 10, text, (button) -> screen.handleTextClick(text.getStyle()), MinecraftClient.getInstance().textRenderer);

        clickable = text.getStyle().getClickEvent() != null;
        // remove underline if no click event
        if (!clickable)
            ((TextAccessor) this).setHoverText(text);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        if (clickable)
            super.playDownSound(soundManager);
    }

    public static Text geturlopen(String text, String url) {
        return Text.of(text).getWithStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))).getFirst();
    }

    public static Text runcommand(String text, String command) {
        return Text.of(text).getWithStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command))).getFirst();
    }
}