package nz.tomasborsje.duskfall.commands;

import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.definitions.MobDefinition;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import nz.tomasborsje.duskfall.registries.MobRegistry;
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
public class SpawnEntityCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // Only give items to players
        if(sender instanceof Player player) {
            String mobId = args[0].toUpperCase();

            // If the definition doesn't exist, tell the player and return
            if(!MobRegistry.Contains(mobId)) {
                player.sendMessage(ChatColor.RED+"No mob registered with ID "+mobId);
                return true;
            }

            // Get definition
            MobDefinition definition = MobRegistry.Get(mobId);

            if(definition == null) {
                player.sendMessage(ChatColor.RED+"No mob registered with ID "+mobId);
                return true;
            }

            // Spawn mob
            EntityHandler.SpawnMob(definition, player.getLocation());
            return true;
        }
        return false;
    }
}
