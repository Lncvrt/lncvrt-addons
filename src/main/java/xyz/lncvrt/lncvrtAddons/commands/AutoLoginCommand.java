package xyz.lncvrt.lncvrtAddons.commands;

import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import meteordevelopment.meteorclient.commands.Command;
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
        builder.then(literal("command")
            .executes(context -> {
                Module autologin = Modules.get().get("auto-login");
                Setting<?> setting = autologin.settings.get("command");
                if (setting instanceof StringSetting) {
                    String currentCommand = ((StringSetting) setting).get();
                    info("Current command is \"/" + currentCommand + "\".");
                } else {
                    error("Failed to retrieve command");
                }
                return SINGLE_SUCCESS;
            })
            .then(argument("command", StringArgumentType.greedyString())
                .executes(context -> {
                    String arg = StringArgumentType.getString(context, "command");

                    Module autologin = Modules.get().get("auto-login");
                    Setting<?> setting = autologin.settings.get("command");
                    if (setting instanceof StringSetting) {
                        ((StringSetting) setting).set(arg);
                        info("Successfully set command to \"/" + arg + "\".");
                    } else {
                        error("Failed to set command");
                    }
                    return SINGLE_SUCCESS;
                }))
        );

        builder.then(literal("delay")
            .executes(context -> {
                Module autologin = Modules.get().get("auto-login");
                Setting<?> setting = autologin.settings.get("delay");
                if (setting instanceof DoubleSetting) {
                    double currentDelay = ((DoubleSetting) setting).get();
                    info("Current delay is " + DECIMAL_FORMAT.format(currentDelay) + "s.");
                } else {
                    error("Failed to retrieve delay");
                }
                return SINGLE_SUCCESS;
            })
            .then(argument("delay", DoubleArgumentType.doubleArg())
                .executes(context -> {
                    double arg = DoubleArgumentType.getDouble(context, "delay");

                    Module autologin = Modules.get().get("auto-login");
                    Setting<?> setting = autologin.settings.get("delay");
                    if (setting instanceof DoubleSetting) {
                        double formattedDelay = Double.parseDouble(DECIMAL_FORMAT.format(arg));
                        ((DoubleSetting) setting).set(formattedDelay);
                        info("Successfully set delay to " + formattedDelay + "s.");
                    } else {
                        error("Failed to set delay");
                    }
                    return SINGLE_SUCCESS;
                }))
        );
    }
}
