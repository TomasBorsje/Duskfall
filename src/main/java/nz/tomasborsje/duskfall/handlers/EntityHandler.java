package nz.tomasborsje.duskfall.handlers;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import nz.tomasborsje.duskfall.core.MMOEntity;
import nz.tomasborsje.duskfall.core.MMOMob;
import nz.tomasborsje.duskfall.core.MMOPlayer;
import nz.tomasborsje.duskfall.core.NMSMob;
import nz.tomasborsje.duskfall.definitions.MobDefinition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R4.CraftWorld;
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
    public static @Nullable MMOEntity GetEntity(org.bukkit.entity.Entity entity) {
        return entities.get(entity.getEntityId());
    }

    /**
     * Get a player by their Bukkit entity. This is a convenience method for GetEntity.
     * @param entity The Bukkit entity.
     * @return The MMOPlayer.
     */
    public static @Nullable MMOPlayer GetPlayer(org.bukkit.entity.Entity entity) {
        return (MMOPlayer) entities.get(entity.getEntityId());
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
    public static void RemoveEntity(org.bukkit.entity.Entity entity) {
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

    /**
     * Spawn a mob in-world from a definition.
     * @param definition The definition of the mob to spawn.
     * @param loc The location to spawn the mob at.
     */
    public static void SpawnMob(MobDefinition definition, Location loc) {

        // Spawn mob by classname
        String className = "nz.tomasborsje.duskfall.definitions.mobs." + definition.classname;
        Entity nmsEntity;
        try {
            Class<?> mobClass = Class.forName(className);
            Object mob = mobClass.getConstructor(Location.class).newInstance(loc);
            if(mob instanceof NMSMob) {
                nmsEntity = (Entity)mob;
                nmsEntity.setPos(loc.getX(), loc.getY(), loc.getZ()); // Set location
                nmsEntity.setCustomNameVisible(true);

                // If living entity, add extra values
                if(nmsEntity instanceof net.minecraft.world.entity.LivingEntity livingEntity) {
                    // Add infinite fire resist effect without particles
                    livingEntity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.FIRE_RESISTANCE, -1, 0, false, false));

                    // If size multiplier is set, add it
                    if(definition.sizeMultiplier != 1.0f) {
                        livingEntity.getAttribute(Attributes.SCALE).addPermanentModifier(new AttributeModifier("SizeMultiplier", definition.sizeMultiplier - 1.0f, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
                    }
                }
                // Add to level
                Level level = ((CraftWorld) loc.getWorld()).getHandle();
                level.addFreshEntity(nmsEntity);
            }
            else {
                throw new RuntimeException("Mob class " + className + " does not implement NMSMob");
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception during mob spawning: " + e);
        }

        // Convert to Bukkit LivingEntity
        LivingEntity bukkitEntity = (LivingEntity) nmsEntity.getBukkitEntity();

        // Create MMOEntity
        MMOEntity entity = new MMOMob(bukkitEntity, definition);

        // Store
        EntityHandler.AddEntity(entity);
    }
}
