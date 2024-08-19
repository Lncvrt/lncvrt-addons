package xyz.lncvrt.lncvrtAddons.modules;

import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public class FallSpeed extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
        .name("speed")
        .description("The speed you will fall at.")
        .defaultValue(1.0d)
        .range(0.1d, 10.0d)
        .decimalPlaces(3)
        .build()
    );

    public FallSpeed() {
        super(Categories.Movement, "fall-speed", "Modifies your movement speed when falling.");
    }
}
