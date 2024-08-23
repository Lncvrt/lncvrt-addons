package xyz.lncvrt.lncvrtAddons.modules;

import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public class AutoLogin extends Module {
    private final SettingGroup sgGeneral = this.settings.getDefaultGroup();

    private final Setting<Double> loginDelay = sgGeneral.add(new DoubleSetting.Builder()
        .name("login-delay")
        .description("Time in seconds to wait before executing the login command.")
        .defaultValue(1.0d)
        .range(0.0d, 10.0d)
        .decimalPlaces(2)
        .build()
    );

    @SuppressWarnings("SpellCheckingInspection")
    private final Setting<String> loginCommand = sgGeneral.add(new StringSetting.Builder()
        .name("login-command")
        .description("The command to use for automatic login (omit the leading '/').")
        .defaultValue("login progemer223")
        .build()
    );

    private final Setting<Boolean> useMessageListener = sgGeneral.add(new BoolSetting.Builder()
        .name("use-message-listener")
        .description("Enable this to listen for a specific message instead of using a delay.")
        .defaultValue(false)
        .build()
    );

    private final Setting<String> messageForListener = sgGeneral.add(new StringSetting.Builder()
        .name("message-for-listener")
        .description("Specify a message that the server sends, which indicates the need to use the login command. The message should include something like '/login <password> <password>', but doesn't need to be the entire message.")
        .defaultValue("/login <password> <password>")
        .build()
    );

    public AutoLogin() {
        super(Categories.Misc, "auto-login", "Automatically logs in when you join a cracked/offline mode server.");
    }
}
