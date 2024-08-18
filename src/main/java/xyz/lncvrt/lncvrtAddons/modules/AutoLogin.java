package xyz.lncvrt.lncvrtAddons.modules;

import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import xyz.lncvrt.lncvrtAddons.LncvrtAddons;

public class AutoLogin extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<Double> delay = sgGeneral.add(new DoubleSetting.Builder()
        .name("delay")
        .description("The amount of delay before the command should be ran (in seconds)")
        .defaultValue(5.0d)
        .range(0.0d, 10.0d)
        .decimalPlaces(2)
        .build()
    );

    @SuppressWarnings("SpellCheckingInspection")
    private final Setting<String> command = sgGeneral.add(new StringSetting.Builder()
        .name("command")
        .description("The login command to use (should not include / at beginning)")
        .defaultValue("login progemer223")
        .build()
    );

    public AutoLogin() {
        super(LncvrtAddons.CATEGORY, "auto-login", "Auto login");
    }
}
