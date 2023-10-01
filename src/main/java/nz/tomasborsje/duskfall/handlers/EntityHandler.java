package nz.tomasborsje.duskfall.handlers;

import nz.tomasborsje.duskfall.core.MMOEntity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Stores all custom entities and their states as the server is running.
 */
public class EntityHandler {
    private static final HashMap<Integer, MMOEntity> entities = new HashMap<>();

    /**
     * Get a custom entity by its Bukkit entity ID.
     * @param entity The Bukkit entity.
     * @return The custom entity.
     */
    public static @Nullable MMOEntity getEntity(LivingEntity entity) {
        return entities.get(entity.getEntityId());
    }

    /**
     * Add a custom entity to the handler.
     * @param entity The custom entity to add.
     */
    public static void addEntity(MMOEntity entity) {
        entities.put(entity.getBukkitEntity().getEntityId(), entity);
    }

    /**
     * Get all custom entities.
     * @return A collection of all custom entities.
     */
    public static Collection<MMOEntity> getEntities() {
        return entities.values();
    }

    /**
     * Tick all custom entities.
     */
    public static void tick() {
        for (MMOEntity entity : entities.values()) {
            entity.tick();
        }
    }
}
