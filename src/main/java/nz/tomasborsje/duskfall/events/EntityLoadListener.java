package nz.tomasborsje.duskfall.events;

import nz.tomasborsje.duskfall.handlers.EntityHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.EntitiesLoadEvent;

public class EntityLoadListener implements Listener {
    @EventHandler
    public static void OnEntityLoad(EntitiesLoadEvent event) {
        // Remove all entities, or else we get leftover entities from the last time the server was running
        for (Entity entity : event.getEntities()) {
            // If this is in the custom entity handler, don't remove
            if (EntityHandler.GetEntity(entity) != null) {
                continue;
            }

            // If not a player, remove
            if (!entity.getType().equals(EntityType.PLAYER)) {
                entity.remove();
            }
        }
    }
}
