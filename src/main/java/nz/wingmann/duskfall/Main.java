package nz.wingmann.duskfall;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin.
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled Duskfall.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled Duskfall.");
    }
}
