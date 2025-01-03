package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent event) {
        MMOPlayer player = EntityHandler.GetPlayer(event.getPlayer());
        if (player == null) {
            return;
        }

        // If the player has a screen open, call the close event
        if (player.ui.isScreenOpen()) {
            player.ui.sendCloseEvent();
        }
    }
}
