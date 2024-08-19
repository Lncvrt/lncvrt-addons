package xyz.lncvrt.lncvrtAddons.mixin;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class AutoLoginMixin {
    @Shadow
    @Final
    private static Logger LOGGER;
    @Unique
    private static int ticksRemaining = 0;
    @Unique
    private static String commandToSend = "";
    @Unique
    private static boolean waitingForMessage = false;
    @Unique
    private static long messageWaitStart = 0;
    @Unique
    private static String messageForListener = "";

    @Inject(method = "onGameJoin", at = @At("RETURN"))
    private void onGameJoin(CallbackInfo ci) {
        Module autologin = Modules.get().get("auto-login");
        if (autologin.isActive()) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.player != null) {
                Setting<?> delaySetting = autologin.settings.get("login-delay");
                Setting<?> commandSetting = autologin.settings.get("login-command");
                Setting<?> useMessageListenerSetting = autologin.settings.get("use-message-listener");
                Setting<?> messageForListenerSetting = autologin.settings.get("message-for-listener");
                double delaySeconds = (double) delaySetting.get();
                int delayTicks = (int) Math.round(delaySeconds * 20);
                String command = (String) commandSetting.get();
                boolean useMessageListener = (boolean) useMessageListenerSetting.get();
                messageForListener = (String) messageForListenerSetting.get();

                if (useMessageListener) {
                    waitingForMessage = true;
                    messageWaitStart = System.currentTimeMillis();
                } else {
                    ticksRemaining = delayTicks;
                    commandToSend = command;
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        if (waitingForMessage) {
            long elapsed = System.currentTimeMillis() - messageWaitStart;
            if (elapsed > 10000) {
                waitingForMessage = false;
                ticksRemaining = (int) Math.round(((double) ticksRemaining / 20) * 20);
            }
        }

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
