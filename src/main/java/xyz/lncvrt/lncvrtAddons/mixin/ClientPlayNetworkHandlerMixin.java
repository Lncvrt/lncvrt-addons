package xyz.lncvrt.lncvrtAddons.mixin;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Unique
    private static int ticksRemaining = 0;
    @Unique
    private static String commandToSend = "";

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void onGameJoin(CallbackInfo ci) {
        Module autologin = Modules.get().get("auto-login");
        if (autologin.isActive()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.player != null) {
                Setting<?> delaySetting = autologin.settings.get("delay");
                Setting<?> commandSetting = autologin.settings.get("command");
                double delaySeconds = (double) delaySetting.get();
                int delayTicks = (int) Math.round(delaySeconds * 20);
                String command = (String) commandSetting.get();

                ticksRemaining = delayTicks;
                commandToSend = command;
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (ticksRemaining > 0) {
            ticksRemaining--;
            if (ticksRemaining == 0) {
                MinecraftClient client = MinecraftClient.getInstance();
                if (client != null && client.player != null && !commandToSend.strip().trim().isEmpty()) {
                    client.player.networkHandler.sendCommand(commandToSend);
                }
            }
        }
    }
}
