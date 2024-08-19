package xyz.lncvrt.lncvrtAddons.commands;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
import meteordevelopment.meteorclient.settings.BoolSetting;
import meteordevelopment.meteorclient.settings.DoubleSetting;
import meteordevelopment.meteorclient.settings.Setting;
import meteordevelopment.meteorclient.settings.StringSetting;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.command.CommandSource;

import java.text.DecimalFormat;

public class AutoLoginCommand extends Command {
    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    public AutoLoginCommand() {
        super("auto-login", "Settings for Auto Login");
    }

    @Override
    public void build(LiteralArgumentBuilder<CommandSource> builder) {
        builder.then(literal("login-command")
            .executes(context -> {
                Module autologin = Modules.get().get("auto-login");
                Setting<?> setting = autologin.settings.get("login-command");
                if (setting instanceof StringSetting) {
                    String currentCommand = ((StringSetting) setting).get();
                    info("Current login command is \"/" + currentCommand + "\".");
                } else {
                    error("Failed to retrieve login command");
                }
                return SINGLE_SUCCESS;
            })
            .then(argument("command", StringArgumentType.greedyString())
                .executes(context -> {
                    String arg = StringArgumentType.getString(context, "command");

                    Module autologin = Modules.get().get("auto-login");
                    Setting<?> setting = autologin.settings.get("login-command");
                    if (setting instanceof StringSetting) {
                        ((StringSetting) setting).set(arg);
                        info("Successfully set login command to \"/" + arg + "\".");
                    } else {
                        error("Failed to set login command");
                    }
                    return SINGLE_SUCCESS;
                }))
        );

        builder.then(literal("login-delay")
            .executes(context -> {
                Module autologin = Modules.get().get("auto-login");
                Setting<?> setting = autologin.settings.get("login-delay");
                if (setting instanceof DoubleSetting) {
                    double currentDelay = ((DoubleSetting) setting).get();
                    info("Current login delay is " + DECIMAL_FORMAT.format(currentDelay) + "s.");
                } else {
                    error("Failed to retrieve login delay");
                }
                return SINGLE_SUCCESS;
            })
            .then(argument("delay", DoubleArgumentType.doubleArg())
                .executes(context -> {
                    double arg = DoubleArgumentType.getDouble(context, "delay");

                    Module autologin = Modules.get().get("auto-login");
                    Setting<?> setting = autologin.settings.get("login-delay");
                    if (setting instanceof DoubleSetting) {
                        double formattedDelay = Double.parseDouble(DECIMAL_FORMAT.format(arg));
                        ((DoubleSetting) setting).set(formattedDelay);
                        info("Successfully set login delay to " + formattedDelay + "s.");
                    } else {
                        error("Failed to set login delay");
                    }
                    return SINGLE_SUCCESS;
                }))
        );

        builder.then(literal("use-message-listener")
            .executes(context -> {
                Module autologin = Modules.get().get("auto-login");
                Setting<?> setting = autologin.settings.get("use-message-listener");
                if (setting instanceof BoolSetting) {
                    boolean currentStatus = ((BoolSetting) setting).get();
                    info("Use message listener is " + (currentStatus ? "enabled" : "disabled") + ".");
                } else {
                    error("Failed to retrieve message listener status");
                }
                return SINGLE_SUCCESS;
            })
            .then(argument("status", BoolArgumentType.bool())
                .executes(context -> {
                    boolean arg = BoolArgumentType.getBool(context, "status");

                    Module autologin = Modules.get().get("auto-login");
                    Setting<?> setting = autologin.settings.get("use-message-listener");
                    if (setting instanceof BoolSetting) {
                        ((BoolSetting) setting).set(arg);
                        info("Successfully set message listener status to " + arg + "( " + (arg ? "enabled" : "disabled") + ").");
                    } else {
                        error("Failed to set message listener status");
                    }
                    return SINGLE_SUCCESS;
                }))
        );

        builder.then(literal("message-for-listener")
            .executes(context -> {
                Module autologin = Modules.get().get("auto-login");
                Setting<?> setting = autologin.settings.get("message-for-listener");
                if (setting instanceof StringSetting) {
                    String currentCommand = ((StringSetting) setting).get();
                    info("Current message for listener is \"" + currentCommand + "\".");
                } else {
                    error("Failed to retrieve message for listener");
                }
                return SINGLE_SUCCESS;
            })
            .then(argument("message", StringArgumentType.greedyString())
                .executes(context -> {
                    String arg = StringArgumentType.getString(context, "message");

                    Module autologin = Modules.get().get("auto-login");
                    Setting<?> setting = autologin.settings.get("message-for-listener");
                    if (setting instanceof StringSetting) {
                        ((StringSetting) setting).set(arg);
                        info("Successfully set message for listener to \"" + arg + "\".");
                    } else {
                        error("Failed to set message for listener");
                    }
                    return SINGLE_SUCCESS;
                }))
        );
    }
}
