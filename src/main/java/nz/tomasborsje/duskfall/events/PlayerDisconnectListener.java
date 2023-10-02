package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerDisconnectListener implements Listener {
    @EventHandler
    public void OnPlayerDisconnect(PlayerQuitEvent event) {
        // Remove the player entity
        EntityHandler.RemoveEntity(event.getPlayer());
    }
}
