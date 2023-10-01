package nz.tomasborsje.duskfall;

import nz.tomasborsje.duskfall.commands.GiveItemCommand;
import nz.tomasborsje.duskfall.events.ServerTickRunner;
import nz.tomasborsje.duskfall.events.WorldLoadListener;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * Main class of the plugin.
 */
public class Duskfall extends JavaPlugin {
    /** The plugin's data folder. */
    public static File dataFolder;
    /** Plugin reference. **/
    public static Plugin plugin;
    /** Tick runner that calls all custom tick events. **/
    ServerTickRunner serverTickRunner = new ServerTickRunner();

    @Override
    public void onEnable() {
        plugin = this;

        Bukkit.getLogger().info("Enabled Duskfall.");

        // Get or create plugin's data folder
        dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                Bukkit.getLogger().info("Created data folder.");
            } else {
                Bukkit.getLogger().warning("Failed to create data folder!");
            }
        }

        // Load items
        ItemRegistry.LoadItemDefinitions(dataFolder);

        // Register commands and event listeners
        registerCommands();
        registerEvents();

        // Start server tick event
        serverTickRunner.runTaskTimer(this, 0, 1);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled Duskfall.");
    }

    void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WorldLoadListener(), this);
    }

    void registerCommands() {
        getCommand("giveitem").setExecutor(new GiveItemCommand());
        Bukkit.getLogger().info("Registered commands.");
    }
}
