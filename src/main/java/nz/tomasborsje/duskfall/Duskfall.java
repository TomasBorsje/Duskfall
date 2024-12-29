package nz.tomasborsje.duskfall;

import nz.tomasborsje.duskfall.commands.GiveItemCommand;
import nz.tomasborsje.duskfall.commands.PrintNbtCommand;
import nz.tomasborsje.duskfall.commands.SpawnEntityCommand;
import nz.tomasborsje.duskfall.commands.TryCraftCommand;
import nz.tomasborsje.duskfall.core.DuskfallWorld;
import nz.tomasborsje.duskfall.events.*;
import nz.tomasborsje.duskfall.handlers.GlobalChatClient;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.registries.MobRegistry;
import nz.tomasborsje.duskfall.registries.RecipeRegistry;
import nz.tomasborsje.duskfall.util.ResourcePackGen;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

/**
 * Main class of the plugin.
 */
public class Duskfall extends JavaPlugin {
    public static File dataFolder;
    public static Plugin plugin;
    public static Logger logger;
    public static GlobalChatClient globalChat;
    public static DuskfallWorld world;
    /**
     * Tick runner that calls all custom tick events.
     **/
    ServerTickRunner serverTickRunner = new ServerTickRunner();

    @Override
    public void onEnable() {
        plugin = this;
        logger = getLogger();

        Duskfall.logger.info("Enabled Duskfall.");

        // Get or create plugin's data folder
        dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            if (dataFolder.mkdir()) {
                Duskfall.logger.info("Created data folder.");
            } else {
                Duskfall.logger.warning("Failed to create data folder!");
            }
        }

        // Load items
        ItemRegistry.LoadItemDefinitions(dataFolder);

        // Load mobs
        MobRegistry.LoadMobDefinitions(dataFolder);

        // Load recipes
        RecipeRegistry.LoadCustomRecipes(dataFolder);

        // Register commands and event listeners
        registerCommands();
        registerEvents();

        // Connect to global chat server
        globalChat = new GlobalChatClient();
        globalChat.connect();

        // Gen resource pack
        ResourcePackGen.GenerateResourcePack();

        // Create world instance
        world = new DuskfallWorld();

        // Start server tick event
        serverTickRunner.runTaskTimer(plugin, 0, 1);
    }

    @Override
    public void onDisable() {
        // Disable connection to global chat server
        globalChat.closeConnection(0, "Duskfall server shutting down.");

        Duskfall.logger.info("Disabled Duskfall.");
    }

    void registerEvents() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new WorldLoadListener(), plugin);
        pluginManager.registerEvents(new ItemUseListener(), plugin);
        pluginManager.registerEvents(new PlayerJoinListener(), plugin);
        pluginManager.registerEvents(new PlayerDisconnectListener(), plugin);
        pluginManager.registerEvents(new EntityHurtEntityListener(), plugin);
        pluginManager.registerEvents(new ChatMessageListener(), plugin);
        pluginManager.registerEvents(new BlockBreakListener(), plugin);
        pluginManager.registerEvents(new InventoryClickListener(), plugin);
        pluginManager.registerEvents(new InventoryCloseListener(), plugin);
        pluginManager.registerEvents(new EntityCombustListener(), plugin);
        pluginManager.registerEvents(new ServerListPingListener(), plugin);
        pluginManager.registerEvents(new EntityLoadListener(), plugin);
        pluginManager.registerEvents(new EntityHurtListener(), plugin);
        pluginManager.registerEvents(new BowFiredListener(), plugin);
        pluginManager.registerEvents(new ArrowHitEntityListener(), plugin);
    }

    void registerCommands() {
        getCommand("giveitem").setExecutor(new GiveItemCommand());
        getCommand("spawnentity").setExecutor(new SpawnEntityCommand());
        getCommand("printnbt").setExecutor(new PrintNbtCommand());
        getCommand("trycraft").setExecutor(new TryCraftCommand());
        Duskfall.logger.info("Registered commands.");
    }
}
