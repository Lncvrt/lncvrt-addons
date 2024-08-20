package xyz.lncvrt.lncvrtAddons;

import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;
import xyz.lncvrt.lncvrtAddons.commands.AutoLoginCommand;
import xyz.lncvrt.lncvrtAddons.event.MessageEventHandler;
import xyz.lncvrt.lncvrtAddons.modules.*;

public class LncvrtAddons extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();

    @Override
    public void onInitialize() {
        LOG.info("Initializing Lncvrt Addons");

        // Modules
        Modules.get().add(new AutoLogin());
        Modules.get().add(new FallSpeed());
        Modules.get().add(new ForceSwim());
        Modules.get().add(new InstantStop());
        Modules.get().add(new LegitSpeed());

        // Commands
        Commands.add(new AutoLoginCommand());

        // Register event handlers
        MessageEventHandler.register();
    }

    @Override
    public String getPackage() {
        return "xyz.lncvrt.lncvrtAddons";
    }

    @Override
    public GithubRepo getRepo() {
        return new GithubRepo("Lncvrt", "lncvrt-addons");
    }
}
