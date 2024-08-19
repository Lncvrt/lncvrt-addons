package xyz.lncvrt.lncvrtAddons.event;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class MessageEventHandler {
    public static void register() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String rawText = message instanceof Text ? message.getString() : String.valueOf((Object) null);
            Module autologin = Modules.get().get("auto-login");

            if (autologin != null) {
                String messageForListener = (String) autologin.settings.get("message-for-listener").get();
                String commandToSend = (String) autologin.settings.get("login-command").get();

                if (autologin.isActive() && !commandToSend.strip().trim().isEmpty() && rawText.contains(messageForListener)) {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client != null && client.player != null) {
                        client.player.networkHandler.sendCommand(commandToSend);
                    }
                }
            }
        });
    }
}
