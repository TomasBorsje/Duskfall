package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.Duskfall;
import nz.tomasborsje.duskfall.core.GlobalMessageType;
import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class InventoryCloseListener implements Listener {
    @EventHandler
    public void OnInventoryClose(InventoryCloseEvent event) {
        MMOPlayer player = EntityHandler.GetPlayer(event.getPlayer());
        if(player == null) { return; }

        // If the player has a screen open, call the close event
        if(player.ui.isScreenOpen()) {
            player.ui.sendCloseEvent();
        }
    }
}
