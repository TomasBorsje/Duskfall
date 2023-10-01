package nz.tomasborsje.duskfall.core;

import org.bukkit.entity.LivingEntity;

/**
 * This is the in-world representation of both players and custom entities.
 */
public interface MMOEntity {
    /**
     * @return The entity's Bukkit entity.
     */
    LivingEntity getBukkitEntity();

    /**
     * Tick the entity.
     */
    void tick();

    /**
     * @return Whether the entity is a player.
     */
    boolean isPlayer();

    /**
     * @return The entity's current health.
     */
    int getHealth();

    /**
     * @param damage The amount of damage to deal to the entity.
     */
    void hurt(int damage);

    /**
     * @return The entity's level.
     */
    int getLevel();

    /**
     * Add a buff to the entity.
     */
    void addBuff(Buff buff);

    /**
     * Kill the entity.
     */
    void kill();
}
