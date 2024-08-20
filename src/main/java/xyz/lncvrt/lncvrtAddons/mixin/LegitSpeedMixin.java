package xyz.lncvrt.lncvrtAddons.mixin;

import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.Rotations;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class LegitSpeedMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void legitSpeed(CallbackInfo ci) {
        Module legitSpeed = Modules.get().get("legit-speed");
        if (legitSpeed.isActive()) {
            PlayerEntity player = (PlayerEntity) (Object) this;

            double velX = player.getVelocity().x;
            double velZ = player.getVelocity().z;

            if (velX != 0 || velZ != 0) {
                float optimalYaw = (float) (MathHelper.atan2(velZ, velX) * (180 / Math.PI)) - 90;
                float optimalPitch = player.getPitch();

                Rotations.rotate(optimalYaw, optimalPitch);
                player.setSprinting(true);
            }
        }
    }
}
