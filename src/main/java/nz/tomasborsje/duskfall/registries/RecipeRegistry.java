package nz.tomasborsje.duskfall.registries;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nz.tomasborsje.duskfall.definitions.RecipeDefinition;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

/**
 * Singleton class that provides access to the recipe registry.
 */
public class RecipeRegistry {
    private static final Gson gson = new GsonBuilder()
            .create();

    private static final HashMap<String, RecipeDefinition> recipeRegistry = new HashMap<>();

    /**
     * Gets a recipe definition from the registry. Note that this is a clone, so can safely be modified.
     *
     * @param id The ID of the recipe definition.
     * @return The recipe definition, null if not found.
     */
    public static RecipeDefinition Get(String id) {
        // Check the recipe exists first
        if (!Contains(id)) {
            Bukkit.getLogger().warning("Attempted to get recipe definition with id " + id + " but it does not exist!");
            return null;
        }
        return recipeRegistry.get(id);
    }

    public static boolean Contains(String id) {
        return recipeRegistry.containsKey(id);
    }

    /**
     * Loads all .json recipes from the plugin's data folder.
     *
     * @param dataFolder The plugin's data folder.
     */
    public static void LoadCustomRecipes(File dataFolder) {
        Bukkit.getLogger().info("Loading recipe schema from " + dataFolder.getAbsolutePath());

        // Get the /recipes subfolder
        File recipesFolder = new File(dataFolder, "recipes");
        if (!recipesFolder.exists()) {
            if (!recipesFolder.mkdir()) {
                Bukkit.getLogger().warning("Failed to create /recipes subfolder!");
            }
        }

        // Get all .json files in the /recipes subfolder
        File[] recipeFiles = recipesFolder.listFiles((dir, name) -> name.endsWith(".json"));

        // Load each recipe
        assert recipeFiles != null;
        for (File recipeFile : recipeFiles) {
            // Read the json string
            String json = "";
            try {
                json = Files.readString(recipeFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Parse a list of recipe definitions
            RecipeDefinition[] recipeDefinitions = gson.fromJson(json, RecipeDefinition[].class);
            Bukkit.getLogger().info("Loading " + recipeDefinitions.length + " recipe definitions from " + recipeFile.getName());

            // Register each recipe definition in this file
            for (RecipeDefinition recipeDefinition : recipeDefinitions) {
                RegisterRecipe(recipeDefinition);
            }
        }

        // Print total loaded definitions
        Bukkit.getLogger().info("Finished loading recipes. Loaded a total of " + recipeRegistry.size() + " recipe definitions.");
    }

    /**
     * Registers a recipe definition into the registry.
     *
     * @param recipeDefinition The recipe definition to register.
     */
    public static void RegisterRecipe(RecipeDefinition recipeDefinition) {
        // Preconditions
        if (recipeDefinition == null) {
            Bukkit.getLogger().warning("Attempted to register null recipe definition!");
            return;
        }
        if (recipeDefinition.getId() == null || recipeDefinition.getId().isEmpty()) {
            Bukkit.getLogger().warning("Attempted to register recipe definition with null or empty id!");
            return;
        }

        // Set each ingredient ID to be uppercase
        for(var ingredient : recipeDefinition.getIngredients()) {
            ingredient.setItemId(ingredient.getItemId().toUpperCase());
        }

        // Set result ID to be uppercase
        recipeDefinition.setResultItemId(recipeDefinition.getResultItemId().toUpperCase());

        // Register the recipe definition
        recipeRegistry.put(recipeDefinition.getId().toUpperCase(), recipeDefinition);
    }
}
