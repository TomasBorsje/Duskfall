package nz.tomasborsje.duskfall.registries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nz.tomasborsje.duskfall.definitions.MobDefinition;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Singleton class that provides access to the mob registry.
 */
public class MobRegistry {
    private static final Gson gson = new GsonBuilder()
            .create();

    private static final HashMap<String, MobDefinition> mobRegistry = new HashMap<>();

    /**
     * Gets a mob definition from the registry. Note that this is a clone, so can safely be modified.
     *
     * @param id The ID of the mob definition.
     * @return The mob definition, null if not found.
     */
    public static MobDefinition Get(String id) {
        // Check the mob exists first
        if (!Contains(id)) {
            Bukkit.getLogger().warning("Attempted to get mob definition with id " + id + " but it does not exist!");
            return null;
        }
        return mobRegistry.get(id).clone();
    }

    public static boolean Contains(String id) {
        return mobRegistry.containsKey(id);
    }

    /**
     * Loads all .json mobs from the plugin's data folder.
     *
     * @param dataFolder The plugin's data folder.
     */
    public static void LoadMobDefinitions(File dataFolder) {
        Bukkit.getLogger().info("Loading mob schema from " + dataFolder.getAbsolutePath());

        // Get the /mobs subfolder
        File mobsFolder = new File(dataFolder, "mobs");
        if (!mobsFolder.exists()) {
            if (!mobsFolder.mkdir()) {
                Bukkit.getLogger().warning("Failed to create /mobs subfolder!");
            }
        }

        // Get all .json files in the /mobs subfolder
        File[] mobFiles = mobsFolder.listFiles((dir, name) -> name.endsWith(".json"));

        // Load each mob
        assert mobFiles != null;
        for (File mobFile : mobFiles) {
            // Read the json string
            String json = "";
            try {
                json = Files.readString(mobFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Parse a list of mob definitions
            MobDefinition[] mobDefinitions = gson.fromJson(json, MobDefinition[].class);
            Bukkit.getLogger().info("Loading " + mobDefinitions.length + " mob definitions from " + mobFile.getName());

            // Register each mob definition in this file
            for (MobDefinition mobDefinition : mobDefinitions) {
                RegisterMob(mobDefinition);
            }
        }

        // Print total loaded definitions
        Bukkit.getLogger().info("Finished loading mobs. Loaded a total of " + mobRegistry.size() + " mob definitions.");
    }

    /**
     * Registers a mob definition into the registry.
     *
     * @param mobDefinition The mob definition to register.
     */
    public static void RegisterMob(MobDefinition mobDefinition) {
        // Preconditions
        if (mobDefinition == null) {
            Bukkit.getLogger().warning("Attempted to register null mob definition!");
            return;
        }
        if (mobDefinition.id == null || mobDefinition.id.isEmpty()) {
            Bukkit.getLogger().warning("Attempted to register mob definition with null or empty id!");
            return;
        }

        // Ensure mob id is in uppercase
        mobDefinition.id = mobDefinition.id.toUpperCase();

        Bukkit.getLogger().info("Registering mob definition with id " + mobDefinition.id);

        // Register the mob definition
        mobRegistry.put(mobDefinition.id, mobDefinition);
    }
}
