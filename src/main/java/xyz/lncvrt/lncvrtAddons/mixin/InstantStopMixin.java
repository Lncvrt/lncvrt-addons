package xyz.lncvrt.lncvrtAddons.mixin;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class InstantStopMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void instantStop(CallbackInfo ci) {
        Module instantStop = Modules.get().get("instant-stop");
        if (instantStop.isActive()) {
            PlayerEntity player = (PlayerEntity) (Object) this;

            if (player == MinecraftClient.getInstance().player && player.forwardSpeed == 0 && player.sidewaysSpeed == 0) {
                player.setVelocity(0, player.getVelocity().y, 0);
            }
        }
    }
}
