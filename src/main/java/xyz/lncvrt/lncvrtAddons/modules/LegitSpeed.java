package xyz.lncvrt.lncvrtAddons.modules;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.renderer.ShapeMode;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import meteordevelopment.meteorclient.utils.player.Rotations;
import meteordevelopment.meteorclient.utils.render.RenderUtils;
import meteordevelopment.meteorclient.utils.render.color.SettingColor;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Unique;

public class LegitSpeed extends Module {
    private final SettingGroup sgRender = settings.createGroup("Render");

    private final Setting<Boolean> render = sgRender.add(new BoolSetting.Builder()
        .name("render")
        .description("Whether to render blocks that have been placed.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Double> renderDistance = sgRender.add(new DoubleSetting.Builder()
        .name("render-distance")
        .description("How far away the shape will render at.")
        .defaultValue(6)
        .range(1, 10)
        .decimalPlaces(0)
        .build()
    );

    private final Setting<ShapeMode> shapeMode = sgRender.add(new EnumSetting.Builder<ShapeMode>()
        .name("shape-mode")
        .description("How the shapes are rendered.")
        .defaultValue(ShapeMode.Both)
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> sideColor = sgRender.add(new ColorSetting.Builder()
        .name("side-color")
        .description("The side color of the target block rendering.")
        .defaultValue(new SettingColor(197, 137, 232, 10))
        .visible(render::get)
        .build()
    );

    private final Setting<SettingColor> lineColor = sgRender.add(new ColorSetting.Builder()
        .name("line-color")
        .description("The line color of the target block rendering.")
        .defaultValue(new SettingColor(197, 137, 232))
        .visible(render::get)
        .build()
    );

    public LegitSpeed() {
        super(Categories.Movement, "legit-speed", "Uses the most optimal yaw rotation and always sprints to make you fast.");
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Module legitSpeed = Modules.get().get("legit-speed");
        if (legitSpeed.isActive()) {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;

            double velX = player.getVelocity().x;
            double velZ = player.getVelocity().z;

            if (velX != 0 || velZ != 0) {
                float optimalYaw = (float) (MathHelper.atan2(velZ, velX) * (180 / Math.PI)) - 90;
                float optimalPitch = player.getPitch();

                Rotations.rotate(optimalYaw, optimalPitch);
                player.setSprinting(true);

                if (!render.get()) return;

                BlockPos pos = getLookingAtPosWithCustomView(optimalYaw, optimalPitch, client.player, client.world);

                if (pos != null) RenderUtils.renderTickingBlock(pos.toImmutable(), sideColor.get(), lineColor.get(), shapeMode.get(), 0, 8, true, false);
            }
        }
    }

    @Unique
    public BlockPos getLookingAtPosWithCustomView(double yaw, double pitch, PlayerEntity player, World world) {
        Vec3d playerPos = player.getPos();

        double yawRad = Math.toRadians(yaw);
        double pitchRad = Math.toRadians(pitch);

        double x = -Math.sin(yawRad) * Math.cos(pitchRad);
        double y = -Math.sin(pitchRad);
        double z = Math.cos(yawRad) * Math.cos(pitchRad);

        Vec3d direction = new Vec3d(x, y, z);

        Vec3d end = playerPos.add(direction.multiply(renderDistance.get()));

        RaycastContext context = new RaycastContext(playerPos, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, player);

        BlockHitResult blockHit = world.raycast(context);
        return blockHit.getBlockPos();
    }
}
