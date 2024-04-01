package nz.tomasborsje.duskfall.commands;

import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.util.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Give a custom item to the player who used the command, with an optional count.
 */
public class GiveItemCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // Only give items to players
        if(sender instanceof Player player) {
            String itemId = args[0].toUpperCase(); // Item id is first arg
            int count = args.length == 2 ? Integer.parseInt(args[1]) : 1; // Item count is second arg or 1 if not specified

            // If the definition doesn't exist, tell the player and return
            if(!ItemRegistry.Contains(itemId)) {
                player.sendMessage(ChatColor.RED+"No item registered with ID "+itemId);
                return true;
            }

            // Get definition
            ItemDefinition definition = ItemRegistry.Get(itemId);

            if(definition == null) {
                player.sendMessage(ChatColor.RED+"No item registered with ID "+itemId);
                return true;
            }

            // Create stack
            ItemStack stack = definition.createDefaultStack();

            // Add item to player's inventory
            stack.setAmount(count);
            player.getInventory().addItem(stack);
            player.sendMessage(ChatColor.GREEN+"Gave "+count+"x "+itemId+"!");

            return true;
        }
        return false;
    }
}
