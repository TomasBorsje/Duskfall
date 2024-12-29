package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void OnInventoryClick(InventoryClickEvent event) {
        MMOPlayer player = EntityHandler.GetPlayer(event.getWhoClicked());
        if (player == null) {
            return;
        }
        if (event.getSlot() == -999) {
            return;
        } // Only register item slot clicks

        // Check if this is a custom inventory
        if (player.ui.isScreenOpen() && event.getClickedInventory() != null && event.getClickedInventory().getHolder() == null) {
            // If so, call the click event
            player.ui.sendClickEvent(event.getSlot());
            event.setCancelled(true);
        }
    }
}
