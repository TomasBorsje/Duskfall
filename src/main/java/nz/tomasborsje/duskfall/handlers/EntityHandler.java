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
    public static @Nullable MMOEntity GetEntity(LivingEntity entity) {
        return entities.get(entity.getEntityId());
    }

    /**
     * Add a custom entity to the handler.
     * @param entity The custom entity to add.
     */
    public static void AddEntity(MMOEntity entity) {
        entities.put(entity.getBukkitEntity().getEntityId(), entity);
    }

    /**
     * Remove a custom entity from the handler.
     * @param entity The custom entity to remove.
     */
    public static void RemoveEntity(MMOEntity entity) {
        entities.remove(entity.getBukkitEntity().getEntityId());
    }

    /**
     * Remove a custom entity from the handler.
     * @param entity The Bukkit entity to remove.
     */
    public static void RemoveEntity(LivingEntity entity) {
        entities.remove(entity.getEntityId());
    }

    /**
     * Get all custom entities.
     * @return A collection of all custom entities.
     */
    public static Collection<MMOEntity> GetEntities() {
        return entities.values();
    }

    /**
     * Tick all custom entities.
     */
    public static void Tick() {
        // Remove any entities that don't exist anymore
        List<Integer> toRemove = new ArrayList<>();
        for (Integer id : entities.keySet()) {
            if (entities.get(id).getBukkitEntity() == null || entities.get(id).getBukkitEntity().isDead()) {
                toRemove.add(id);
            }
        }
        for (Integer id : toRemove) {
            entities.remove(id);
        }

        // Tick all entities
        for (MMOEntity entity : entities.values()) {
            entity.tick();
        }
    }
}
