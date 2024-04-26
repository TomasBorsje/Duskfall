package nz.tomasborsje.duskfall.events;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityCombustListener implements Listener {
    @EventHandler
    public static void onEntityCombust(EntityCombustEvent event) {
        // Disable zombies and skeletons from burning in daylight
        if (event.getEntity().getType() == EntityType.ZOMBIE || event.getEntity().getType() == EntityType.SKELETON) {
            event.setCancelled(true);
        }
    }
}
