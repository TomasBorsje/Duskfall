package nz.tomasborsje.duskfall;

import nz.tomasborsje.duskfall.commands.GiveItemCommand;
import nz.tomasborsje.duskfall.commands.SpawnEntityCommand;
import nz.tomasborsje.duskfall.events.*;
import nz.tomasborsje.duskfall.handlers.GlobalChatClient;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.registries.MobRegistry;
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
    public static GlobalChatClient globalChat;
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

        // Load mobs
        MobRegistry.LoadMobDefinitions(dataFolder);

        // Register commands and event listeners
        registerCommands();
        registerEvents();

        // Connect to global chat server
        globalChat = new GlobalChatClient();
        globalChat.connect();

        // Start server tick event
        serverTickRunner.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void onDisable() {
        // Disable connection to global chat server
        globalChat.closeConnection(0, "Duskfall server shutting down.");

        Bukkit.getLogger().info("Disabled Duskfall.");
    }

    void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WorldLoadListener(), plugin);
        pluginManager.registerEvents(new ItemUseListener(), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerDisconnectListener(), plugin);
        pluginManager.registerEvents(new EntityHurtEntityListener(), plugin);
        pluginManager.registerEvents(new ChatMessageListener(), plugin);
    }

    void registerCommands() {
        getCommand("giveitem").setExecutor(new GiveItemCommand());
        getCommand("spawnentity").setExecutor(new SpawnEntityCommand());
        Bukkit.getLogger().info("Registered commands.");
    }
}
