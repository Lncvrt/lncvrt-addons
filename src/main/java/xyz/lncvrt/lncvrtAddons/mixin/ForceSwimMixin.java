package xyz.lncvrt.lncvrtAddons.mixin;

import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class ForceSwimMixin {
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void checkAndSwim(CallbackInfo ci) {
        Module forceSwim = Modules.get().get("force-swim");
        if (forceSwim.isActive()) {
            PlayerEntity player = (PlayerEntity) (Object) this;
            Setting<?> onlyInWaterSetting = forceSwim.settings.get("only-in-water");
            boolean onlyInWater = (boolean) onlyInWaterSetting.get();

            if (!onlyInWater || (player.isSubmergedInWater())) {
                if (!player.isSwimming() && !player.getAbilities().flying) {
                    player.setSwimming(true);
                }
            }
        }
    }
}
