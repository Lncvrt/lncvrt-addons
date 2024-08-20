package xyz.lncvrt.lncvrtAddons.mixin;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class FallSpeedMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void onTickMovement(CallbackInfo ci) {
        Module fallSpeed = Modules.get().get("fall-speed");
        if (fallSpeed.isActive()) {
            PlayerEntity player = (PlayerEntity) (Object) this;

            if (player.getVelocity().y < 0 && !player.isOnGround()) {
                Setting<?> speedSetting = fallSpeed.settings.get("speed");
                Setting<?> disableInWaterSetting = fallSpeed.settings.get("disable-in-water");
                double speed = (double) speedSetting.get();
                boolean disableInWater = (boolean) disableInWaterSetting.get();

                if (!disableInWater || player.getWorld().getBlockState(player.getBlockPos().down()).getBlock() != Blocks.WATER) {
                    Vec3d newVelocity = player.getVelocity().multiply(1, speed, 1);
                    player.setVelocity(newVelocity);
                }
            }
        }
    }
}
