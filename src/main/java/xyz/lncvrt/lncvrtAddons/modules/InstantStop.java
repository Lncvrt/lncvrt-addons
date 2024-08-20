package xyz.lncvrt.lncvrtAddons.modules;

import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;

public class InstantStop extends Module {
    public InstantStop() {
        super(Categories.Movement, "instant-stop", "Instantly stops when not pressing a key.");
    }
}
