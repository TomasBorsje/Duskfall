package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        // Create new MMOPlayer to track the player's stats
        MMOPlayer player = new MMOPlayer(event.getPlayer());
        // Register the player entity
        EntityHandler.AddEntity(player);
    }
}
