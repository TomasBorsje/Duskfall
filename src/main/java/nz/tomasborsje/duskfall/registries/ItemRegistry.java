package nz.tomasborsje.duskfall.registries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Singleton class that provides access to the item registry.
 */
public class ItemRegistry {
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ItemDefinition.class, new ItemDefinitionDeserializer())
            .create();

    private static final HashMap<String, ItemDefinition> itemRegistry = new HashMap<>();

    /**
     * Loads all .json items from the plugin's data folder.
     * @param dataFolder The plugin's data folder.
     */
    public static void LoadItemDefinitions(File dataFolder) {
        Bukkit.getLogger().info("Loading item schema from " + dataFolder.getAbsolutePath());

        // Get the /items subfolder
        File itemsFolder = new File(dataFolder, "items");
        if (!itemsFolder.exists()) {
            if(!itemsFolder.mkdir()) {
                Bukkit.getLogger().warning("Failed to create /items subfolder!");
            }
        }

        // Get all .json files in the /items subfolder
        File[] itemFiles = itemsFolder.listFiles((dir, name) -> name.endsWith(".json"));

        // Load each item
        assert itemFiles != null;
        for (File itemFile : itemFiles) {
            // Read the json string
            String json = "";
            try {
                json = Files.readString(itemFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Parse a list of item definitions
            ItemDefinition[] itemDefinitions = gson.fromJson(json, ItemDefinition[].class);
            Bukkit.getLogger().info("Loading " + itemDefinitions.length + " item definitions from " + itemFile.getName());

            // Register each item definition in this file
            for (ItemDefinition itemDefinition : itemDefinitions) {
                RegisterItem(itemDefinition);
            }
        }

        // Print total loaded definitions
        Bukkit.getLogger().info("Finished loading items. Loaded a total of " + itemRegistry.size() + " item definitions.");
    }

    /**
     * Registers an item definition into the registry.
     * @param itemDefinition The item definition to register.
     */
    public static void RegisterItem(ItemDefinition itemDefinition) {
        // Preconditions
        if (itemDefinition == null) {
            Bukkit.getLogger().warning("Attempted to register null item definition.");
            return;
        }
        if (itemDefinition.id == null || itemDefinition.id.isEmpty()) {
            Bukkit.getLogger().warning("Attempted to register item definition with null or empty id.");
            return;
        }

        // Register the item definition
        itemRegistry.put(itemDefinition.id, itemDefinition);
    }
}
