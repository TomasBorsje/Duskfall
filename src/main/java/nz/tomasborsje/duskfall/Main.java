package nz.tomasborsje.duskfall;

import nz.tomasborsje.duskfall.registries.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main class of the plugin.
 */
public class Main extends JavaPlugin {
    /**
     * The plugin's data folder.
     */
    public static File dataFolder;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled Duskfall.");

        // Get or create plugin's data folder
        dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            if(dataFolder.mkdir()) {
                Bukkit.getLogger().info("Created data folder.");
            } else {
                Bukkit.getLogger().warning("Failed to create data folder!");
            }
        }

        // Load items
        ItemRegistry.LoadItemDefinitions(dataFolder);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled Duskfall.");
    }
}
