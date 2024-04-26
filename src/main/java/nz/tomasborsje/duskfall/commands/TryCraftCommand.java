package nz.tomasborsje.duskfall.commands;

import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.definitions.RecipeDefinition;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.registries.RecipeRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Attempts to craft an item using the player's inventory, using the given recipe ID.
 */
public class TryCraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // Only give items to players
        if(sender instanceof Player player) {
            String recipeId = args[0].toUpperCase(); // Recipe id is first arg
            // If the definition doesn't exist, tell the player and return
            if(!RecipeRegistry.Contains(recipeId)) {
                player.sendMessage(ChatColor.RED+"No recipe registered with ID "+recipeId);
                return true;
            }

            // Get recipe
            RecipeDefinition definition = RecipeRegistry.Get(recipeId);

            // Check if we can craft the recipe
            if(definition.canCraft(player.getInventory())) {
                // Craft the item
                ItemStack result = definition.craft(player.getInventory());
                // If the result is null, the player doesn't have the required ingredients
                if(result == null) {
                    player.sendMessage(ChatColor.RED+"You don't have the required ingredients to craft this item.");
                    return true;
                }
                // Add the item to the player's inventory
                player.getInventory().addItem(result);
                player.sendMessage(ChatColor.GREEN+"Successfully crafted "+result.getAmount()+"x "+result.getItemMeta().getDisplayName());
            } else {
                player.sendMessage(ChatColor.RED+"You don't have the required ingredients to craft this item.");
            }
            return true;
        }
        return false;
    }
}
