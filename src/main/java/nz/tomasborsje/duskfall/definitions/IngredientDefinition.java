package nz.tomasborsje.duskfall.definitions;

import com.google.gson.annotations.SerializedName;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an ingredient in a recipe.
 * Holds the item ID and the amount of the item required.
 */
public class IngredientDefinition {

    @SerializedName("itemId")
    private String itemId = null;
    @SerializedName("count")
    private final int count = 1;

    public IngredientDefinition() { }

    /**
     * Returns true if the given stack fulfills the ingredient.
     * @param stack The stack to check.
     * @return True if the stack fulfills the ingredient.
     */
    public boolean matches(ItemStack stack) {
        if(stack == null) {
            return false;
        }
        return ItemUtil.GetCustomId(stack).equals(itemId) && stack.getAmount() >= count;
    }

    /**
     * Returns the item ID of the ingredient.
     * @return The item ID of the ingredient.
     */
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the amount of the ingredient required.
     * @return The amount of the ingredient required.
     */
    public int getCount() {
        return count;
    }

    public String toString() {
        return "IngredientDefinition{" +
                "itemId='" + itemId + '\'' +
                ", count=" + count +
                '}';
    }
}
