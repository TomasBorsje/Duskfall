package nz.tomasborsje.duskfall;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin.
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled Duskfall, this is a different class now!!.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled Duskfall.");
    }
}
