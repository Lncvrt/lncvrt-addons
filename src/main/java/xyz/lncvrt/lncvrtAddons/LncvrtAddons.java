package xyz.lncvrt.lncvrtAddons;

import xyz.lncvrt.lncvrtAddons.commands.AutoLoginCommand;
import xyz.lncvrt.lncvrtAddons.modules.AutoLogin;
import com.mojang.logging.LogUtils;
import meteordevelopment.meteorclient.addons.GithubRepo;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.commands.Commands;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class LncvrtAddons extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Lncvrt Addons");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Lncvrt Addons");

        // Modules
        Modules.get().add(new AutoLogin());

        // Commands
        Commands.add(new AutoLoginCommand());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
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
