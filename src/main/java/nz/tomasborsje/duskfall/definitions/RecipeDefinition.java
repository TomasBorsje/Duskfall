package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class RecipeDefinition {
    @SerializedName("recipeId")
    private final String id = null;

    @SerializedName("ingredients")
    private final IngredientDefinition[] ingredients = new IngredientDefinition[0];

    @SerializedName("resultItemId")
    private String resultItemId = null;

    public RecipeDefinition() {
    }

    public boolean canCraft(Inventory inventory) {
        // Check if every ingredient is met
        for (IngredientDefinition ingredient : ingredients) {
            boolean ingredientSatisfied = false;
            // Check if any of the items in the inventory match the ingredient
            for (ItemStack stack : inventory.getContents()) {
                if (ingredient.matches(stack)) {
                    ingredientSatisfied = true;
                    break;
                }
            }
            // If no item matched the ingredient, the recipe is not satisfied
            if (!ingredientSatisfied) {
                return false;
            }
        }
        return true;
    }

    public ItemStack getResultItem() {
        // Get the item with the given ID
        return ItemRegistry.Get(resultItemId).createDefaultStack();
    }

    public ItemStack craft(Inventory inventory) {
        // If can't craft, return
        if (!canCraft(inventory)) {
            return null;
        }

        // Remove the ingredients from the inventory
        for (IngredientDefinition ingredient : ingredients) {
            for (ItemStack stack : inventory.getContents()) {
                if (ingredient.matches(stack)) {
                    stack.setAmount(stack.getAmount() - ingredient.getCount());
                    break;
                }
            }
        }
        return getResultItem();
    }

    public String getId() {
        return id;
    }

    public String getResultItemId() {
        return resultItemId;
    }

    public void setResultItemId(String resultItemId) {
        this.resultItemId = resultItemId;
    }

    public IngredientDefinition[] getIngredients() {
        return ingredients;
    }

    public String toString() {
        return "RecipeDefinition{" +
                "id='" + id + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", resultItemId='" + resultItemId + '\'' +
                '}';
    }
}
