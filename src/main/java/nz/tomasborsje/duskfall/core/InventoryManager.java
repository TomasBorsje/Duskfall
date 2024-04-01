package nz.tomasborsje.duskfall.core;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Manages the player's inventory.
 */
public class InventoryManager {
    private final MMOPlayer player;
    public InventoryManager(MMOPlayer player) {
        this.player = player;
    }

    /**
     * Adds an item to the player's inventory.
     * @param itemStack The item to add.
     * @param reason The reason for adding the item.
     */
    public void addItem(ItemStack itemStack, ItemAddReason reason) {
        // Add to the player's inventory
        HashMap<Integer, ItemStack> leftover = player.getBukkitEntity().getInventory().addItem(itemStack);

        // If there is any leftover, drop it on the ground
        if(!leftover.isEmpty()) {
            player.getBukkitEntity().getWorld().dropItemNaturally(player.getBukkitEntity().getLocation(), leftover.get(0));
        }

        // If the item was added because of a drop, send a message to the player
        String message = ChatColor.GRAY+"You "+reason.verb+" "+itemStack.getAmount()+"x "+itemStack.getItemMeta().getDisplayName()+ChatColor.GRAY+".";
        player.getBukkitEntity().sendMessage(message);
    }

    /**
     * Adds an item to the player's inventory with the GENERIC reason.
     * @param itemStack The item to add.
     */
    public void addItem(ItemStack itemStack) {
        addItem(itemStack, ItemAddReason.GENERIC);
    }
}
