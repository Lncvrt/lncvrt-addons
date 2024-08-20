package xyz.lncvrt.lncvrtAddons.modules;

import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public class ForceSwim extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<Boolean> onlyInWater = sgGeneral.add(new BoolSetting.Builder()
        .name("only-in-water")
        .description("Weather it should only be enabled when in water or all the time.")
        .defaultValue(true)
        .build()
    );

    public ForceSwim() {
        super(Categories.Movement, "force-swim", "Forces you to swim all the time or just in water.");
    }
}
