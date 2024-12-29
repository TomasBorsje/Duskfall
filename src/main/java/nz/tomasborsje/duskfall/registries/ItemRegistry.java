package nz.tomasborsje.duskfall.registries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
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
     * Returns a random item definition from the registry.
     * Note: THIS IS A TESTING METHOD AND SHOULD NOT BE USED IN PRODUCTION CODE.
     *
     * @return A random item definition.
     */
    @Deprecated
    public static ItemDefinition GetRandomItem() {
        int num = (int) (Math.random() * itemRegistry.size());
        for (ItemDefinition t : itemRegistry.values()) if (--num < 0) return t;
        throw new AssertionError();
    }

    /**
     * Gets an item definition from the registry. Note that this is a clone, so can safely be modified.
     *
     * @param id The ID of the item definition.
     * @return The item definition, null if not found.
     */
    public static ItemDefinition Get(String id) {
        // Check the item exists first
        if (!Contains(id)) {
            Duskfall.logger.warning("Attempted to get item definition with id " + id + " but it does not exist!");
            return null;
        }
        return itemRegistry.get(id).clone();
    }

    /**
     * Gets all item definitions from the registry.
     *
     * @return An unmodifiable collection of all item definitions.
     */
    public static Collection<ItemDefinition> GetAllItems() {
        return Collections.unmodifiableCollection(itemRegistry.values());
    }

    public static boolean Contains(String id) {
        return itemRegistry.containsKey(id);
    }

    /**
     * Loads all .json items from the plugin's data folder.
     *
     * @param dataFolder The plugin's data folder.
     */
    public static void LoadItemDefinitions(File dataFolder) {
        // Get the /items subfolder
        File itemsFolder = new File(dataFolder, "items");
        if (!itemsFolder.exists()) {
            if (!itemsFolder.mkdir()) {
                Duskfall.logger.warning("Failed to create /items subfolder!");
            }
        }
        Duskfall.logger.info("Loading item schema from " + itemsFolder.getAbsolutePath());

        // Get all .json files in the /items subfolder
        File[] itemFiles = itemsFolder.listFiles((dir, name) -> name.endsWith(".json"));
        System.out.println("Number of files: " + itemFiles.length);

        // Load each item
        assert itemFiles != null;
        for (File itemFile : itemFiles) {
            String json = "";
            try {
                json = Files.readString(itemFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Parse a list of item definitions
            ItemDefinition[] itemDefinitions = gson.fromJson(json, ItemDefinition[].class);
            Duskfall.logger.info("Loading " + itemDefinitions.length + " item definitions from " + itemFile.getName());
            for (ItemDefinition itemDefinition : itemDefinitions) {
                RegisterItem(itemDefinition);
            }
        }

        Duskfall.logger.info("Finished loading item definitions. Loaded a total of " + itemRegistry.size() + " item definitions.");
    }

    /**
     * Registers an item definition into the registry.
     *
     * @param itemDefinition The item definition to register.
     */
    public static void RegisterItem(ItemDefinition itemDefinition) {
        // Preconditions
        if (itemDefinition == null) {
            Duskfall.logger.warning("Attempted to register null item definition!");
            return;
        }
        if (itemDefinition.id == null || itemDefinition.id.isEmpty()) {
            Duskfall.logger.warning("Attempted to register item definition with null or empty id!");
            return;
        }

        // Ensure item id is in uppercase
        itemDefinition.id = itemDefinition.id.toUpperCase();

        // Ensure type is in uppercase if not null
        if (itemDefinition.type != null) {
            itemDefinition.type = itemDefinition.type.toUpperCase();
        }

        // Ensure material is in uppercase
        itemDefinition.material = itemDefinition.material.toUpperCase();

        // Register the item definition
        itemRegistry.put(itemDefinition.id, itemDefinition);
    }
}
