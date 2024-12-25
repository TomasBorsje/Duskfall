package nz.tomasborsje.duskfall.commands;

import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import nz.tomasborsje.duskfall.definitions.ItemDefinition;
import nz.tomasborsje.duskfall.registries.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_21_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Prints all NBT tags, their values, and their types for the player's currently held item.
 */
public class PrintNbtCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        // If not a player, return
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command.");
            return true;
        }
        // Get held item
        Player player = (Player) sender;
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        // If no item is held, return
        if (heldItem == null || heldItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "You must be holding an item to use this command.");
            return true;
        }

        // Get ItemStack's NBT
        CompoundTag nmsStack = CraftItemStack.asNMSCopy(heldItem).get(DataComponents.CUSTOM_DATA).getUnsafe();

        // Print out each NBT tag value and type in the chat
        player.sendMessage(ChatColor.GREEN + "NBT tags for held item:");
        // If none, show none. Else, print all
        if (nmsStack.isEmpty()) {
            player.sendMessage(ChatColor.GRAY + "None");
        } else {
            for (String key : nmsStack.getAllKeys()) {
                player.sendMessage(ChatColor.GRAY + key + ": " + nmsStack.get(key).getAsString() + " (" + nmsStack.get(key).getType() + ")");
            }
        }
        return true;
    }
}
